package br.com.victor.safebox.gateway.http;



import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.gateway.http.json.UpdateRequest;
import br.com.victor.safebox.gateway.http.mapper.ClientMapper;
import br.com.victor.safebox.usecase.UpdateClientUseCase;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/v1/client/update")
@Api(value = "/v1/client/update", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UpdateClientController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ClientMapper mapper;
    private final UpdateClientUseCase updateClientUseCase;
    @Autowired
    public UpdateClientController(final ClientMapper mapper,final  UpdateClientUseCase updateClientUseCase) {
        this.mapper = mapper;
        this.updateClientUseCase = updateClientUseCase;
    }


    @ApiOperation(value = "singup")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "create new user - retorn user saved"),
            @ApiResponse(code = 408, message = "Request timeout"),
            @ApiResponse(code = 422, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestBody Mono<UpdateRequest> client)  {
        log.info("create a new user - {}",client.block().getName());
        Preconditions.checkArgument(Objects.nonNull(client));
        String newPassword = client.block().getNewPassword();
        updateClientUseCase.execute(client.cast(Client.class),newPassword);
    }


}
