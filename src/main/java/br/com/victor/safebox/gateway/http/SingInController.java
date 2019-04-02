package br.com.victor.safebox.gateway.http;


import br.com.victor.safebox.domain.Client;
import br.com.victor.safebox.gateway.http.json.CreateUserRequest;
import br.com.victor.safebox.gateway.http.json.CreateUserResponse;
import br.com.victor.safebox.gateway.http.json.SingInReponse;
import br.com.victor.safebox.gateway.http.json.SingInRequest;
import br.com.victor.safebox.gateway.http.mapper.ClientMapper;
import br.com.victor.safebox.usecase.RegisterNewUser;
import br.com.victor.safebox.usecase.SingInUserCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/auth/")
@Api(value = "/v1/auth/", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SingInController {

	private ClientMapper mapper;
	private SingInUserCase singInUserCase;

	@Autowired
	public SingInController(ClientMapper mapper, SingInUserCase singInUserCase) {
		this.mapper = mapper;
		this.singInUserCase = singInUserCase;
	}

	private final Logger log = LoggerFactory.getLogger(getClass());


	@ApiOperation(value = "authentication")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "sing in - return token valid"),
			@ApiResponse(code = 408, message = "Request timeout"),
			@ApiResponse(code = 422, message = "Validation error"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<SingInReponse> singIn(@RequestBody SingInRequest client)  {
		log.info("authentication user - {}  - {}",client.getUsername(), LocalDateTime.now());
		String token = singInUserCase.execute(mapper.mapToDomain(client));
		SingInReponse response = new SingInReponse();
		response.setToken(token);
		return  ResponseEntity.ok().body(response);
	}


}
