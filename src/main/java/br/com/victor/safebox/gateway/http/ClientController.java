package br.com.victor.safebox.gateway.http;


import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.gateway.http.json.CreateUserRequest;
import br.com.victor.safebox.gateway.http.json.CreateUserResponse;
import br.com.victor.safebox.gateway.http.mapper.ClientMapper;
import br.com.victor.safebox.usecase.RegisterNewUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/v1/client/")
@Api(value = "/v1/client/", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ClientController {

	private ClientMapper mapper;
	private RegisterNewUser registerNewUser;

	@Autowired
	public ClientController(ClientMapper mapper, RegisterNewUser registerNewUser) {
		this.mapper = mapper;
		this.registerNewUser = registerNewUser;
	}

	private final Logger log = LoggerFactory.getLogger(getClass());
	

	@ApiOperation(value = "singup")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "create new user - retorn user saved"),
			@ApiResponse(code = 408, message = "Request timeout"),
			@ApiResponse(code = 422, message = "Validation error"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CreateUserResponse> save(@RequestBody CreateUserRequest client)  {
		log.info("create a new user - {}",client.getName());
		Client savedClient = registerNewUser.execute(mapper.mapToDomain(client));
		CreateUserResponse response = (CreateUserResponse) mapper.mapToResponse(savedClient,CreateUserResponse.class);
		return  ResponseEntity.ok().body(response);
	}


}
