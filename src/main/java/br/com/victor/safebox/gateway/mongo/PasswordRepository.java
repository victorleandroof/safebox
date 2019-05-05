package br.com.victor.safebox.gateway.mongo;

import br.com.victor.safebox.gateway.mongo.entity.PasswordEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PasswordRepository extends MongoRepository<PasswordEntity,String> {

    PasswordEntity findById(String id);
}
