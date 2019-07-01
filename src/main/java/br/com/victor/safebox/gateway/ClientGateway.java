package br.com.victor.safebox.gateway;



import java.util.List;

import br.com.victor.safebox.domain.Client;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ClientGateway {

	Flux<Client> listAll();

	Mono<Client> getById(String id);

	Mono<Client> saveOrUpdate(Mono<Client> client);

	void delete(String id);

	Mono<Client> findByUsername(String username);

}
