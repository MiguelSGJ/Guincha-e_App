package com.guinchae.guinchae;

import com.guinchae.guinchae.model.RoleModel;
import com.guinchae.guinchae.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class GuinchaeApplication {

	public static void main(String[] args) {	SpringApplication.run(GuinchaeApplication.class, args);	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			if(roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(
						RoleModel.builder().name("USER").build()
				);
			}
		};
	}

}
