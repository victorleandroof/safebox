package br.com.victor.safebox.gateway;



import java.util.List;

import br.com.victor.safebox.domain.Client;
import org.springframework.stereotype.Component;

@Component
public interface ClientGateway {

	List<Client> listAll();

	Client getById(String id);

	Client saveOrUpdate(Client client);

	void delete(String id);
	
	Client findByUsername(String username);

}
