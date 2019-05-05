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

    public Password execute(Password password,String username) throws Exception{
        Client client = clientGateway.findByUsername(username);
        password.setClient(client);
        password.setPassword(Base64.getEncoder()
                .encodeToString(cryp.encrypt(client.getPublicKey(),password.getPassword().getBytes())));
        Password passwordSaved = passwordGateway.save(password);
        client.getListPasswords().add(passwordSaved);
        clientGateway.saveOrUpdate(client);
        return passwordSaved;
    }


}
