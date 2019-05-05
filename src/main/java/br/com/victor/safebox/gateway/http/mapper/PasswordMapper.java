package br.com.victor.safebox.gateway.http.mapper;

import br.com.victor.safebox.domain.Password;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("passwordMapperHttp")
public class PasswordMapper {

    public Password mapToDomain(Object request){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(request,Password.class);
    }


    public <D> D mapToResponse(Object source, Class<D> destinationType) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(source,destinationType);
    }

}
