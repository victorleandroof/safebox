package br.com.victor.safebox.gateway.mongo;

import java.util.List;
import java.util.stream.Collectors;

import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.mapper.ClientMapper;
import br.com.victor.safebox.gateway.mongo.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.victor.safebox.gateway.ClientGateway;;

@Service
public class ClientGatewayImpl implements ClientGateway {

	private ClientRepository clientRepository;
	private ClientMapper clientMapper;


	@Autowired
	public ClientGatewayImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
	}

	@Override
	public List<Client> listAll() {
		return clientRepository.findAll().stream().map((c)->clientMapper.mapToDomain(c)).collect(Collectors.toList());
	}

	@Override
	public Client getById(String id) {
		return clientMapper.mapToDomain(clientRepository.findById(id));
	}

	@Override
	public Client saveOrUpdate(Client client) {
		ClientEntity saveClient =  clientRepository.save(clientMapper.mapToEntity(client));
		return clientMapper.mapToDomain(saveClient);
	}

	@Override
	public void delete(String id) {
		clientRepository.delete(id);
	}

	@Override
	public Client findByUsername(String username) {
		return clientMapper.mapToDomain(clientRepository.findByUsername(username));
	}

}
