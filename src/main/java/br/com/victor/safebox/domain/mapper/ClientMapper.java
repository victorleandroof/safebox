package br.com.victor.safebox.domain.mapper;

import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.gateway.mongo.entity.ClientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("ClientMapper")
public class ClientMapper {

    public ClientEntity mapToEntity(Client client){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(client,ClientEntity.class);
    }

    public Client mapToDomain(ClientEntity clientEntity){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(clientEntity,Client.class);
    }
}
