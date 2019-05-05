package br.com.victor.safebox.gateway.mongo;

import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.domain.mapper.PasswordMapper;
import br.com.victor.safebox.gateway.PasswordGateway;
import br.com.victor.safebox.gateway.mongo.entity.PasswordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class PasswordGatewayImpl implements PasswordGateway {

    private final PasswordRepository passwordRepository;
    private final PasswordMapper passwordMapper;

    @Autowired
    public PasswordGatewayImpl(final PasswordRepository passwordRepository,final PasswordMapper passwordMapper) {
        this.passwordRepository = passwordRepository;
        this.passwordMapper = passwordMapper;
    }
    @Override
    public Password save(Password password) {
        PasswordEntity passwordEntity = passwordMapper.mapToEntity(password);
        return passwordMapper.mapToDomain(passwordRepository.save(passwordEntity));
    }
    @Override
    public Password update(Password password) {
        PasswordEntity passwordEntity = passwordMapper.mapToEntity(password);
        return passwordMapper.mapToDomain(passwordRepository.save(passwordEntity));
    }
    @Override
    public Password findById(String id) {
        return passwordMapper.mapToDomain(passwordRepository.findById(id));
    }

    @Override
    public List<Password> passwords() {
        return passwordRepository.findAll().stream().map(passwordMapper::mapToDomain).collect(Collectors.toList());
    }

}
