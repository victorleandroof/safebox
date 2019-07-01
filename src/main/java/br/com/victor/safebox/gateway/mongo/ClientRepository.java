package br.com.victor.safebox.gateway.mongo;


import java.util.List;


import br.com.victor.safebox.gateway.mongo.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ClientRepository extends ReactiveMongoRepository<ClientEntity, String> {
	Mono<ClientEntity> save(Mono<ClientEntity> clientEntityMono);
	Mono<ClientEntity> findByUsername(String username);
	Mono<ClientEntity> findById(String id);
	Flux<ClientEntity> findAll();
	Mono<Void> delete(String id);
}
