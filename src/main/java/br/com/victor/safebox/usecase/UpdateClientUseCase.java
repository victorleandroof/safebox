package br.com.victor.safebox.usecase;

import br.com.victor.safebox.config.cryptography.CryptographyUtil;
import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.gateway.ClientGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UpdateClientUseCase {

    private final ClientGateway clientGateway;
    private final ModalValidator<Client> validator;
    @Autowired
    public UpdateClientUseCase(ClientGateway clientGateway,ModalValidator<Client> validator) {
        this.clientGateway = clientGateway;
        this.validator = validator;
    }

    public Client execute(Client client,String oldPassword){
        validator.execute(client);
        try {
            final CryptographyUtil cryp = new CryptographyUtil();
            cryp.saveKeysKeyStore(client.getUsername(), client.getPassword());
            client.setPublicKey(cryp.getPublicKeyStr());
            client.setPassword(Base64.getEncoder()
                    .encodeToString((cryp.encrypt(client.getPassword().getBytes()))));
        }catch (Exception e){
            e.printStackTrace();
        }
        return clientGateway.saveOrUpdate(client);
    }
}
