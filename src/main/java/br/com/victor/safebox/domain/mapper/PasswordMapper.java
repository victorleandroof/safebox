package br.com.victor.safebox.domain.mapper;

import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.gateway.mongo.entity.PasswordEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("passwordMapper")
public class PasswordMapper {


    public Password mapToDomain(PasswordEntity passwordEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(passwordEntity,Password.class);
    }

    public PasswordEntity mapToEntity(Password password){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(password,PasswordEntity.class);
    }

}
