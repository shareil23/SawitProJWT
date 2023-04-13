package com.example.sawitProJwt;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "User Service",
				version = "1.0",
				description = "SawitPro Assesment",
				license = @License(name = "Apache 2.0", url = "http://foo.bar")
		)
)
@SpringBootApplication
@EnableJpaAuditing
public class SawitProJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SawitProJwtApplication.class, args);
	}

}
