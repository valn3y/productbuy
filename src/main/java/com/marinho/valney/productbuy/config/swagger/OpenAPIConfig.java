package com.marinho.valney.productbuy.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static com.marinho.valney.productbuy.config.swagger.SecurityConstants.SCHEME_BEARER_AUTH;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Product Buy API", version = "1.0", description = "Product Buy API Application"))
@SecurityScheme(name = SCHEME_BEARER_AUTH, scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class OpenAPIConfig {

}
