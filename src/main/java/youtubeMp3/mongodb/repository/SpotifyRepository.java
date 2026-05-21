package youtubeMp3.mongodb.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import youtubeMp3.mongodb.model.SpotifyDownloadModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpotifyRepository extends ReactiveMongoRepository<SpotifyDownloadModel, String> {
    @Query("{ '$or': [ { 'deleted': false }, { 'deleted': { '$exists': false } } ] }")
    Flux<SpotifyDownloadModel> findByDeletedFalse();

    @Query("{ '_id': ?0, '$or': [ { 'deleted': false }, { 'deleted': { '$exists': false } } ] }")
    Mono<SpotifyDownloadModel> findByIdAndDeletedFalse(String id);
}
