package com.platform.ppdbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
@PropertySources({
	@PropertySource("classpath:/application-secret.properties"),
})
public class PpdBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpdBackendApplication.class, args);
	}

}
