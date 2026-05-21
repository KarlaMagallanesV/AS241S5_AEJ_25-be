package youtubeMp3.mongodb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import youtubeMp3.mongodb.model.VideoModel;
import youtubeMp3.mongodb.repository.VideoRepository;
import youtubeMp3.mongodb.util.UrlParserUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class VideoService {

    @Value("${rapidapi.key}")
    private String apiKey;

    @Value("${rapidapi.host}")
    private String apiHost;

    private final WebClient webClient;
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
        this.webClient = WebClient.builder()
                .baseUrl("https://youtube-mp36.p.rapidapi.com")
                .build();
    }

    public Mono<VideoModel> convertVideo(String urlOrId) {
        String videoId = UrlParserUtil.extractYoutubeId(urlOrId);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/dl")
                        .queryParam("id", videoId)
                        .build())
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(VideoModel.class)
                .flatMap(video -> {
                    video.setVideoId(videoId);
                    video.setThumbnail(UrlParserUtil.generateYoutubeThumbnail(videoId));
                    return videoRepository.save(video);
                });
    }

    public Flux<VideoModel> getAllVideos() {
        return videoRepository.findAll();
    }

    public Mono<VideoModel> getVideoById(String id) {
        return videoRepository.findByIdAndDeletedFalse(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Video not found")));
    }

    public Mono<VideoModel> updateVideo(String id, Boolean favorite) {
        return getVideoById(id)
                .flatMap(video -> {
                    if (favorite != null)
                        video.setFavorite(favorite);
                    return videoRepository.save(video);
                });
    }

    public Mono<Void> deleteVideo(String id) {
        return getVideoById(id)
                .flatMap(video -> {
                    video.setDeleted(true);
                    video.setDeletedAt(LocalDateTime.now());
                    return videoRepository.save(video);
                }).then();
    }

    public Mono<Void> restoreVideo(String id) {
        return videoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Video not found")))
                .flatMap(video -> {
                    video.setDeleted(false);
                    video.setDeletedAt(null);
                    return videoRepository.save(video);
                }).then();
    }
}
