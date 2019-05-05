package br.com.victor.safebox.config.security;

import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.SecurityConstants;
import br.com.victor.safebox.domain.UserAuthentication;
import br.com.victor.safebox.domain.exception.AuthenticationException;
import br.com.victor.safebox.gateway.ClientGateway;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JsonWebTokenAuthenticationService implements TokenAuthenticationService {

    @Value("${security.token.secret.key}")
    private String secretKey;
    private final ClientGateway clientGateway;

    @Autowired
    public JsonWebTokenAuthenticationService(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    public Authentication authenticate(final HttpServletRequest request) {
        final String token = request.getHeader(SecurityConstants.AUTH_HEADER_NAME);
        final Jws<Claims> tokenData = parseToken(token);
        if (tokenData != null) {
            final Client user = getUserFromToken(tokenData);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
       return null;
    }

    private Jws<Claims> parseToken(final String token) {
        if (token != null) {
            try {
                return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                    | SignatureException | IllegalArgumentException e) {
                throw  new AuthenticationException("usuario nao autenticado");
            }
        }
        return null;
    }

    private Client getUserFromToken(final Jws<Claims> tokenData) {
        try {
            final String username = tokenData.getBody().get("username").toString();
            return clientGateway.findByUsername(username);
        } catch (Exception e) {
            throw  new AuthenticationException("usuario nao localizado na base de dados");
        }
    }
}
