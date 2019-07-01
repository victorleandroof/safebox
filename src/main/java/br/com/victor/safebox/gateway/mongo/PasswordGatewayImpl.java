package br.com.victor.safebox.gateway.mongo;

import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.domain.mapper.PasswordMapper;
import br.com.victor.safebox.gateway.PasswordGateway;
import br.com.victor.safebox.gateway.mongo.entity.PasswordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Password> save(Mono<Password> password) {
        Mono<PasswordEntity> passwordEntity = password.cast(PasswordEntity.class);
        return passwordRepository.save(password.cast(PasswordEntity.class)).cast(Password.class);
    }
    @Override
    public Mono<Password> update(Mono<Password> password) {
        Mono<PasswordEntity> passwordEntity = password.cast(PasswordEntity.class);
        return passwordRepository.save(passwordEntity).cast(Password.class);
    }
    @Override
    public Mono<Password> findById(String id) {
        return passwordRepository.findById(id).cast(Password.class);
    }

    @Override
    public Flux<Password> passwords() {
        Flux<PasswordEntity> passwordEntityFlux = passwordRepository.findAll();
        return passwordEntityFlux.cast(Password.class);
    }

}
