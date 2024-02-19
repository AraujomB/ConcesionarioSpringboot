package org.jrotero.cocheB;

import java.util.Set;

import org.jrotero.cocheB.models.ERoles;
import org.jrotero.cocheB.models.RolEntity;
import org.jrotero.cocheB.models.UserEntity;
import org.jrotero.cocheB.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ConcesionarioBApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcesionarioBApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Bean
	CommandLineRunner init() {
		return args -> {



			UserEntity userEntity = UserEntity.builder()

					.email("pablo@mail.com")

					.username("pablo")

					.password(passwordEncoder.encode("1234"))

					.roles(Set.of(RolEntity.builder()

							.roles(ERoles.valueOf(ERoles.ADMIN.name()))

							.build()))

					.build();



			UserEntity userEntity2 = UserEntity.builder()

					.email("anna@mail.com")

					.username("anna")

					.password(passwordEncoder.encode("1234"))

					.roles(Set.of(RolEntity.builder()

							.roles(ERoles.valueOf(ERoles.USER.name()))

							.build()))

					.build();



			UserEntity userEntity3 = UserEntity.builder()

					.email("camila@mail.com")

					.username("camila")

					.password(passwordEncoder.encode("1234"))

					.roles(Set.of(RolEntity.builder()

							.roles(ERoles.valueOf(ERoles.SUPERADMIN.name()))

							.build()))

					.build();



			userRepository.save(userEntity);

			userRepository.save(userEntity2);

			userRepository.save(userEntity3);

		};
	}
	
}
