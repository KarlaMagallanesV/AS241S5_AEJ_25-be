package youtubeMp3.mongodb.rest;

import org.springframework.web.bind.annotation.*;
import youtubeMp3.mongodb.model.SpotifyDownloadModel;
import youtubeMp3.mongodb.service.SpotifyService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyRest {

    private final SpotifyService service;

    public SpotifyRest(SpotifyService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<SpotifyDownloadModel> getAllSongs() {
        return service.getAllSongs();
    }

    @GetMapping("/{id}")
    public Mono<SpotifyDownloadModel> getSongById(@PathVariable String id) {
        return service.getSongById(id);
    }

    @PostMapping("/download")
    public Mono<SpotifyDownloadModel> downloadSong(@RequestParam String songId) {
        return service.downloadSong(songId);
    }

    @PutMapping("/{id}")
    public Mono<SpotifyDownloadModel> updateSong(@PathVariable String id, @RequestParam(required = false) Boolean favorite) {
        return service.updateSong(id, favorite);
    }

    @PatchMapping("/{id}/delete")
    public Mono<String> deleteSong(@PathVariable String id) {
        return service.deleteSong(id).thenReturn("Eliminado con éxito el id: " + id);
    }

    @PatchMapping("/{id}/restore")
    public Mono<String> restoreSong(@PathVariable String id) {
        return service.restoreSong(id).thenReturn("Restaurado con éxito el id: " + id);
    }
}
