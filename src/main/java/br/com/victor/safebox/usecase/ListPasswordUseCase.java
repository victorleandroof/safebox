package br.com.victor.safebox.usecase;

import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.gateway.ClientGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ListPasswordUseCase {

    private final ClientGateway clientGateway;

    @Autowired
    public ListPasswordUseCase(final ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public Flux<Password> execute(final String username){
         Mono<Client> clientMono = clientGateway.findByUsername(username);
         return clientMono.block().getListPasswords();
    }


}
