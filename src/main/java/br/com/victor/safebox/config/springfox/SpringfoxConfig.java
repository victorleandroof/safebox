package br.com.victor.safebox.config.springfox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Date;
import java.util.List;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "io.swagger")
public class SpringfoxConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/.*";
	
	@Bean
	public Docket documentation() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
	            .apiInfo(apiInfo())
	            .pathMapping("/")
	            .apiInfo(ApiInfo.DEFAULT)
	            .forCodeGeneration(true)
	            .genericModelSubstitutes(ResponseEntity.class)
	            .ignoredParameterTypes(Pageable.class)
	            .ignoredParameterTypes(java.sql.Date.class)
	            .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
	            .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
	            .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
	            .securityContexts(Lists.newArrayList(securityContext()))
	            .securitySchemes(Lists.newArrayList(apiKey()))
	            .useDefaultResponseMessages(false);

	        docket = docket.select()
	            .paths(regex(DEFAULT_INCLUDE_PATTERN))
	            .build();
	        return docket;

	}

	@Bean
	public UiConfiguration uiConfig() {
		return new UiConfiguration((String) null);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Documentation api simples")
				.version("1.0")
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}
}
