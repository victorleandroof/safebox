package br.com.victor.safebox.gateway;

import br.com.victor.safebox.domain.Password;

import java.util.List;

public interface PasswordGateway  {

    Password save(Password password);
    Password update(Password password);
    Password findById(String id);
    List<Password> passwords();
}
