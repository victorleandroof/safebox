package br.com.victor.safebox.gateway;

import br.com.victor.safebox.domain.Password;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PasswordGateway  {

    Mono<Password> save(Mono<Password> password);
    Mono<Password> update(Mono<Password> password);
    Mono<Password> findById(String id);
    Flux<Password> passwords();
}
