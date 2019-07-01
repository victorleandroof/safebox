package br.com.victor.safebox.usecase;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.equals;
import static com.google.common.base.Preconditions.checkArgument;

import br.com.victor.safebox.config.cryptography.CryptographyUtil;
import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.exception.AuthenticationException;
import br.com.victor.safebox.gateway.ClientGateway;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@Component
public class SingInUserCase {

    private final ClientGateway clientGateway;
    private static int tokenExpirationTime = 365;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private  final CryptographyUtil cryp;

    @Value("${security.token.secret.key}")
    private String tokenKey;

    @Autowired
    public SingInUserCase(final ClientGateway clientGateway, final CryptographyUtil cryp) {
        this.clientGateway = clientGateway;
        this.cryp = cryp;
    }

    public String execute(Mono<Client> client){
        checkArgument(nonNull(client),"client request not found");
        checkArgument(isNotBlank(client.block().getUsername()),"username cannot be blank!");
        checkArgument(isNotBlank(client.block().getPassword()),"username cannot be blank!");
        checkArgument(isNotEmpty(client.block().getUsername()),"username cannot be empty!");
        checkArgument(isNotEmpty(client.block().getPassword()),"username cannot be empty!");

        Mono<Client> clientFound = clientGateway.findByUsername(client.block().getUsername());
        try {
            String password = new String(
                    cryp.decryptFromKeyStore(client.block().getUsername(),
                            client.block().getPassword(),clientFound.block().getPassword()));
            clientFound.block().setPassword(password);

        }catch (Exception e){
            log.info(e.getMessage());
        }
        if(isEmpty(clientFound.block().getPassword()))throw new AuthenticationException("Usuario ou senha invalidos!!");
        else if(!clientFound.block().getPassword().equals(client.block().getPassword()))throw new AuthenticationException("Usuario ou senha invalidos!!");

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("clientType", "user");
        tokenData.put("username", clientFound.block().getUsername());
        tokenData.put("token_create_date", LocalDateTime.now());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, tokenExpirationTime);
        tokenData.put("token_expiration_date", calendar.getTime());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(tokenData);
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }

    public static void setTokenExpirationTime(int tokenExpirationTime) {
        SingInUserCase.tokenExpirationTime = tokenExpirationTime;
    }
}
