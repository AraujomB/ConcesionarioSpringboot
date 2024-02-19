package org.jrotero.coches.repositories;

import java.util.Optional;

import org.jrotero.coches.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	Optional<UserEntity>findByUsername(String username);

	@Query("select u from UserEntity u where u.username = ?1")
	Optional<String> getUsernameSelect(String username);
	
}
