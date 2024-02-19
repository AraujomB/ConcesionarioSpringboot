package org.jrotero.cocheB.security;

import org.jrotero.cocheB.jwt.JwtUtils;
import org.jrotero.cocheB.security.filters.JwtAuthenticationFilter;
import org.jrotero.cocheB.security.filters.jwtAuthorizationFilter;
import org.jrotero.cocheB.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailService;
	
	@Autowired
	private jwtAuthorizationFilter authorizationFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
		JwtAuthenticationFilter authF = new JwtAuthenticationFilter(jwtUtils);
		authF.setAuthenticationManager(authenticationManager);
		authF.setFilterProcessesUrl("/login");
		
		return httpSecurity
				.csrf(config ->config.disable())
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/get-all").permitAll();
					auth.anyRequest().authenticated();
				})
				.sessionManagement(session ->{
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				}).addFilter(authF)
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class).build();
				/*.httpBasic(withDefaults()) //para pedir usuario y contraseña
				.build(); // para hacer peticiones de autenticacion basic*/
		
	};
	
	@Bean
	UserDetailsService userDetailService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("Pepe").password("1234567")
				.roles().build());
		return manager;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailService)
				.passwordEncoder(passwordEncoder)
				.and().build();
	}
}
