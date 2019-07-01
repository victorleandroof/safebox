package br.com.victor.safebox.usecase;

import br.com.victor.safebox.config.cryptography.CryptographyUtil;
import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.gateway.ClientGateway;
import br.com.victor.safebox.gateway.PasswordGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class SavePasswordUserCase {

    private final PasswordGateway passwordGateway;
    private final ClientGateway clientGateway;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private  final CryptographyUtil cryp;

    @Autowired
    public SavePasswordUserCase(final PasswordGateway passwordGateway,final ClientGateway clientGateway,final CryptographyUtil cryp) {
        this.passwordGateway = passwordGateway;
        this.clientGateway = clientGateway;
        this.cryp = cryp;
    }

    public Mono<Password> execute(Mono<Password> password, String username) throws Exception{
        Mono<Client> clientMono = clientGateway.findByUsername(username);
        password.block().setClient(clientMono);
        password.block().setPassword(Base64.getEncoder()
                .encodeToString(cryp.encrypt(clientMono.block().getPublicKey(),password.block().getPassword().getBytes())));
        Mono<Password> passwordSaved = passwordGateway.save(password);
        Flux<Password> passwordFlux = Flux.just(passwordSaved.block());
        Flux<Password> passwordFluxSaved = clientMono.block().getListPasswords();
        clientMono.block().setListPasswords(Flux.merge(passwordFlux,passwordFluxSaved));
        clientGateway.saveOrUpdate(clientMono);
        return passwordSaved;
    }


}
