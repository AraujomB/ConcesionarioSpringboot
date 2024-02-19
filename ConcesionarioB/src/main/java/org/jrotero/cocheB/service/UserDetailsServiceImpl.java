package org.jrotero.cocheB.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jrotero.cocheB.models.UserEntity;
import org.jrotero.cocheB.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserEntity userEntity = repository.findByUsername(username).orElseThrow(() ->
		new UsernameNotFoundException("Usuario ".concat(username).concat(" no existe")));
		
		Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRoles().name())))
				.collect(Collectors.toSet());
		
		return new User(userEntity.getUsername(),
				userEntity.getPassword(),
				true,
				true,
				true,
				true,
				authorities);
	}
	
}
