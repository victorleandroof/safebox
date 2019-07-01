package br.com.victor.safebox.gateway.http;


import br.com.victor.safebox.config.security.TokenAuthenticationService;
import br.com.victor.safebox.domain.Password;
import br.com.victor.safebox.gateway.http.json.ListPasswordResponse;
import br.com.victor.safebox.gateway.http.mapper.PasswordMapper;
import br.com.victor.safebox.usecase.ListPasswordUseCase;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/password")
@Api(value = "/v1/password", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ListPasswordController {

    private final ListPasswordUseCase listPasswordUseCase;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TokenAuthenticationService tokenAuthenticationService;
    private final PasswordMapper passwordMapper;
    @Autowired
    public ListPasswordController(
                                  final  ListPasswordUseCase listPasswordUseCase,
                                  final PasswordMapper passwordMapper,
                                  final TokenAuthenticationService tokenAuthenticationService,
                                  final SavePasswordUserCase savePasswordUserCase) {
        this.listPasswordUseCase = listPasswordUseCase;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.passwordMapper = passwordMapper;
    }

    @ApiOperation(value = "register password")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "create new user - retorn user saved"),
            @ApiResponse(code = 408, message = "Request timeout"),
            @ApiResponse(code = 422, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(method = RequestMethod.GET)
    public Flux<ListPasswordResponse> findAll() throws Exception {
        log.info("saved password {}", LocalDateTime.now());
        String username = tokenAuthenticationService.getUserLogged().getUsername();
        Preconditions.checkArgument(Objects.nonNull(username),"username not found");
        Flux<Password> passwords = listPasswordUseCase.execute(username);
        return passwords.cast(ListPasswordResponse.class);
    }


}
