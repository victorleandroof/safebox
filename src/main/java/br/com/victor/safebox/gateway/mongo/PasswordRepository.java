package br.com.victor.safebox.gateway.mongo;

import br.com.victor.safebox.gateway.mongo.entity.PasswordEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public interface PasswordRepository extends ReactiveMongoRepository<PasswordEntity,String> {
    Mono<PasswordEntity> save(Mono<PasswordEntity> passwordEntity);
    Mono<PasswordEntity> findById(String id);
}
