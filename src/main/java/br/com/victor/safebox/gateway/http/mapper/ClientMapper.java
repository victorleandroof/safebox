package br.com.victor.safebox.gateway.http.mapper;

import br.com.victor.safebox.domain.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("ClientMapperHttp")
public class ClientMapper {

    public Client mapToDomain(Object request){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(request,Client.class);
    }


    public Object mapToResponse(Client client, Class<?> responseClasse){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(client,responseClasse);
    }

}
