package com.restapi;

import com.restapi.config.security.JwtAuthenticationFilter;
import com.restapi.entity.Role;
import com.restapi.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class RestapiApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RestapiApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}



	@Autowired
	private RoleRepo roleRepo;


	@Override
	public void run(String... args) throws Exception {
	logger.info("Logging info");
	logger.warn("Logging warn");
	logger.error("Logging error");
	logger.trace("Logging trace");
	logger.debug("Logging debug");

		if(!roleRepo.existsByName("ROLE_ADMIN")){
			Role role1 = new Role();
			role1.setId(UUID.randomUUID().toString());
			role1.setName("ROLE_ADMIN");
			roleRepo.save(role1);
		}

		if(!roleRepo.existsByName("ROLE_USER")){
			Role role2 = new Role();
			role2.setId(UUID.randomUUID().toString());
			role2.setName("ROLE_USER");
			roleRepo.save(role2);
		}

	}
}
