package br.com.victor.safebox.gateway.http;


import br.com.victor.safebox.config.security.TokenAuthenticationService;
import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.domain.SecurityConstants;
import br.com.victor.safebox.gateway.http.json.CreateUserRequest;
import br.com.victor.safebox.gateway.http.json.CreateUserResponse;
import br.com.victor.safebox.gateway.http.json.SavePasswordRequest;
import br.com.victor.safebox.gateway.http.json.SavePasswordResponse;
import br.com.victor.safebox.gateway.http.mapper.PasswordMapper;
import br.com.victor.safebox.usecase.SavePasswordUserCase;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/v1/password/register")
@Api(value = "/v1/password/register", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SavePasswordController {

    private final SavePasswordUserCase savePasswordUserCase;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TokenAuthenticationService tokenAuthenticationService;
    private final PasswordMapper passwordMapper;
    @Autowired
    public SavePasswordController(final PasswordMapper passwordMapper,
                                  final TokenAuthenticationService tokenAuthenticationService,
                                  final SavePasswordUserCase savePasswordUserCase) {
        this.savePasswordUserCase = savePasswordUserCase;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.passwordMapper = passwordMapper;
    }

    @ApiOperation(value = "register password")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "create new user - retorn user saved"),
            @ApiResponse(code = 408, message = "Request timeout"),
            @ApiResponse(code = 422, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(method = RequestMethod.POST)
    public Mono<SavePasswordResponse> save(@RequestBody Mono<SavePasswordRequest> savePasswordRequest) throws Exception {
        log.info("saved password {}", LocalDateTime.now());
        String username = tokenAuthenticationService.getUserLogged().getUsername();
        Preconditions.checkArgument(Objects.nonNull(username),"username not found");
        Preconditions.checkArgument(Objects.nonNull(savePasswordRequest),"request invalid");
        Mono<Password> passwordSaved = savePasswordUserCase.execute(savePasswordRequest.cast(Password.class),username);
        return passwordSaved.cast(SavePasswordResponse.class);
    }

}
