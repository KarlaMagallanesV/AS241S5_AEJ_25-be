package youtubeMp3.mongodb.rest;

import org.springframework.web.bind.annotation.*;
import youtubeMp3.mongodb.model.VideoModel;
import youtubeMp3.mongodb.service.VideoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/youtube")
public class VideoRest {

    private final VideoService service;

    public VideoRest(VideoService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<VideoModel> getAllVideos() {
        return service.getAllVideos();
    }

    @GetMapping("/{id}")
    public Mono<VideoModel> getVideoById(@PathVariable String id) {
        return service.getVideoById(id);
    }

    @PostMapping("/convert")
    public Mono<VideoModel> convertVideo(@RequestParam String videoId) {
        return service.convertVideo(videoId);
    }

    @PutMapping("/{id}")
    public Mono<VideoModel> updateVideo(@PathVariable String id, @RequestParam(required = false) Boolean favorite) {
        return service.updateVideo(id, favorite);
    }

    @PatchMapping("/{id}/delete")
    public Mono<String> deleteVideo(@PathVariable String id) {
        return service.deleteVideo(id).thenReturn("Eliminado con éxito el id: " + id);
    }

    @PatchMapping("/{id}/restore")
    public Mono<String> restoreVideo(@PathVariable String id) {
        return service.restoreVideo(id).thenReturn("Restaurado con éxito el id: " + id);
    }
}
