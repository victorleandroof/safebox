package br.com.victor.safebox.gateway.mongo;

import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.mapper.ClientMapper;
import br.com.victor.safebox.gateway.mongo.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.victor.safebox.gateway.ClientGateway;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;;

@Component
@Transactional
public class ClientGatewayImpl implements ClientGateway {

	private ClientRepository clientRepository;
	private ClientMapper clientMapper;


	@Autowired
	public ClientGatewayImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
	}

	@Override
	public Flux<Client> listAll() {
		Flux<ClientEntity> clientEntityFlux = clientRepository.findAll();
		return clientEntityFlux.cast(Client.class);
	}

	@Override
	public Mono<Client> getById(String id) {
		return clientRepository.findById(id).cast(Client.class);
	}

	@Override
	public Mono<Client> saveOrUpdate(Mono<Client> client) {
		Mono<ClientEntity> saveClient =  clientRepository.save(client.cast(ClientEntity.class));
		return saveClient.cast(Client.class);
	}

	@Override
	public void delete(String id) {
		clientRepository.delete(id);
	}

	@Override
	public Mono<Client> findByUsername(String username) {
		return clientRepository.findByUsername(username).cast(Client.class);
	}

}
