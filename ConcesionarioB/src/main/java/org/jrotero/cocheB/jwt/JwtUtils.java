package org.jrotero.cocheB.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	@Value("${jwt.time.expiration}")
	private String timeExpiration;
	
	//generador de acceso por token
	public String generateAccessToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration))) //establecemos el tiempo de validez del token
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256) //Algoritmo de JWT (jwt.io en web para mas informacion)
				.compact();
	}
	
	//obtener username del token
	public String getUsernameToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public <T> T getClaim(String token, Function<Claims, T>claimsFunction) {
		Claims claims = extractAllClaims(token);
		return claimsFunction.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignatureKey()).
				build()
				.parseClaimsJws(token)
				.getBody();
	}

	//obtener la firma
	private Key getSignatureKey() {
		byte[] keyBy = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBy);
	}
	
	//Validar el token
	public boolean isTokenvalid(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getSignatureKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
			return true;
		} catch (Exception e) {
			log.error("Invalid token, Error ".concat(e.getMessage()));
			return false;
		}
	}
	
}
