package br.com.victor.safebox.config.security;

import br.com.victor.safebox.domain.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;


public interface TokenAuthenticationService {
    Authentication authenticate(HttpServletRequest request);
    Client getUserLogged();

}
