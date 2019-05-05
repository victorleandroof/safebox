package br.com.victor.safebox.gateway.mongo;


import java.util.List;


import br.com.victor.safebox.gateway.mongo.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ClientRepository extends MongoRepository<ClientEntity, String> {
	
	ClientEntity findByUsername(String username);
	ClientEntity findById(String id);
	List<ClientEntity> findAll();

}
