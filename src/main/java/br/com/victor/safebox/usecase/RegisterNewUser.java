package br.com.victor.safebox.usecase;

import br.com.victor.safebox.config.cryptography.CryptographyUtil;
import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.gateway.ClientGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class RegisterNewUser {

    private final ClientGateway clientGateway;
    private final ModalValidator<Client> validator;
    private  final CryptographyUtil cryp;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    public RegisterNewUser(final ClientGateway clientGateway,final ModalValidator<Client> validator, final CryptographyUtil cryp) {
        this.clientGateway = clientGateway;
        this.validator = validator;
        this.cryp = cryp;
    }

    public Mono<Client> execute(Mono<Client> client){
            validator.execute(client.block());

            try {
                cryp.saveKeysKeyStore(client.block().getUsername(), client.block().getPassword());
                client.block().setPublicKey(cryp.getPublicKeyStr());
                client.block().setPassword(Base64.getEncoder()
                        .encodeToString((cryp.encrypt(client.block().getPassword().getBytes()))));
                return clientGateway.saveOrUpdate(client);
            }catch (Exception e){
                log.warn(e.getMessage());
            }

            return  null;
    }

}
