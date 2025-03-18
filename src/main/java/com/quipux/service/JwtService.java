package com.quipux.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.quipux.model.TokenResponse;
import com.quipux.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;
	
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;
	
	public String generarToken(final Usuario usuario) {
		return buildToken(usuario, jwtExpiration);
	}
	
	public String generarRefreshToken (final Usuario usuario) {
		return buildToken(usuario, refreshExpiration);
	}
	
	
	
	private String buildToken (final Usuario usuario, final long expiration) {
		return Jwts.builder()
				.id(usuario.getIdUsuario().toString())
				.claims(Map.of("name", usuario.getUsuario()))
				.subject(usuario.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey())
				.compact();
	}
	
	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private boolean isTokenExpired (final String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public boolean isTokenValid (final String token, final Usuario usuario) {
		final String username = extractUsername(token);
		return (username.equals(usuario.getEmail())) && !isTokenExpired(token);
	}
	
	public String extractUsername (final String token) {
		final Claims jwtToken = Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return jwtToken.getSubject();
	} 
	
	private Date extractExpiration (final String token) {
		final Claims jwtToken = Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return jwtToken.getExpiration();
	}
	
}
