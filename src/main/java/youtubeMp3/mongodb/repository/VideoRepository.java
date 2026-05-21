package youtubeMp3.mongodb.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import youtubeMp3.mongodb.model.VideoModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoRepository extends ReactiveMongoRepository<VideoModel, String> {
    @Query("{ '$or': [ { 'deleted': false }, { 'deleted': { '$exists': false } } ] }")
    Flux<VideoModel> findByDeletedFalse();

    @Query("{ '_id': ?0, '$or': [ { 'deleted': false }, { 'deleted': { '$exists': false } } ] }")
    Mono<VideoModel> findByIdAndDeletedFalse(String id);
}
