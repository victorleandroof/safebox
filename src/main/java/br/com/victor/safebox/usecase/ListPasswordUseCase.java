package br.com.victor.safebox.usecase;

import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.gateway.ClientGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListPasswordUseCase {

    private final ClientGateway clientGateway;

    @Autowired
    public ListPasswordUseCase(final ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public List<Password> execute(final String username){
        final Client client = clientGateway.findByUsername(username);
        return client.getListPasswords();
    }


}
