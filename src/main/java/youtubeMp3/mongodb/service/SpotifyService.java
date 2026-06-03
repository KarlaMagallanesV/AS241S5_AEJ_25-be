package youtubeMp3.mongodb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

import youtubeMp3.mongodb.model.SpotifyDownloadModel;
import youtubeMp3.mongodb.repository.SpotifyRepository;
import youtubeMp3.mongodb.util.UrlParserUtil;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import tools.jackson.core.JacksonException;

@Service
public class SpotifyService {

    @Value("${rapidapi.spotify.key}")
    private String apiKey;

    @Value("${rapidapi.spotify.host}")
    private String apiHost;

    private final WebClient webClient;
    private final SpotifyRepository spotifyRepository;
    private final ObjectMapper objectMapper;

    public SpotifyService(SpotifyRepository spotifyRepository) {
        this.spotifyRepository = spotifyRepository;
        this.webClient = WebClient.builder()
                .baseUrl("https://spotify-music-mp3-downloader-api.p.rapidapi.com")
                .build();
        this.objectMapper = JsonMapper.builder().build();
    }

    public Mono<SpotifyDownloadModel> downloadSong(String urlOrId) {
        String songId = UrlParserUtil.extractSpotifyId(urlOrId);
        String spotifyUrl = "https://open.spotify.com/track/" + songId;

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/download")
                        .queryParam("link", spotifyUrl)
                        .build())
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    SpotifyDownloadModel model = new SpotifyDownloadModel();
                    model.setSongId(songId);
                    model.setStatus("success");
                    try {
                        JsonNode jsonNode = objectMapper.readTree(response);
                        if (jsonNode.has("data")) {
                            JsonNode data = jsonNode.get("data");
                            if (data.has("medias") && data.get("medias").isArray()
                                    && data.get("medias").size() > 0) {
                                JsonNode firstMedia = data.get("medias").get(0);
                                if (firstMedia.has("url")) {
                                    model.setDownloadLink(firstMedia.get("url").asText());
                                }
                            }
                            if (data.has("title"))
                                model.setTitle(data.get("title").asText());
                            if (data.has("author"))
                                model.setArtist(data.get("author").asText());
                            if (data.has("thumbnail"))
                                model.setCoverImage(data.get("thumbnail").asText());
                        }
                    } catch (JacksonException e) {
                        model.setStatus("parse_error: " + e.getMessage());
                    }
                    return spotifyRepository.save(model);
                })
                .onErrorResume(e -> {
                    SpotifyDownloadModel errorModel = new SpotifyDownloadModel();
                    errorModel.setSongId(songId);
                    errorModel.setStatus("error: " + e.getMessage());
                    return spotifyRepository.save(errorModel);
                });
    }

    public Flux<SpotifyDownloadModel> getAllSongs() {
        return spotifyRepository.findAll();
    }

    public Mono<SpotifyDownloadModel> getSongById(String id) {
        return spotifyRepository.findByIdAndDeletedFalse(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Song not found")));
    }

    public Mono<SpotifyDownloadModel> updateSong(String id, Boolean favorite) {
        return getSongById(id)
                .flatMap(song -> {
                    if (favorite != null)
                        song.setFavorite(favorite);
                    return spotifyRepository.save(song);
                });
    }

    public Mono<Void> deleteSong(String id) {
        return getSongById(id)
                .flatMap(song -> {
                    song.setDeleted(true);
                    song.setDeletedAt(LocalDateTime.now());
                    return spotifyRepository.save(song);
                }).then();
    }

    public Mono<Void> restoreSong(String id) {
        return spotifyRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Song not found")))
                .flatMap(song -> {
                    song.setDeleted(false);
                    song.setDeletedAt(null);
                    return spotifyRepository.save(song);
                }).then();
    }
}
