package com.quipux.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quipux.model.Token;
import com.quipux.model.TokenResponse;
import com.quipux.model.Usuario;
import com.quipux.repository.TokenRepository;
import com.quipux.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class AutenticacionService {

	private final UsuarioRepository usuarioRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AutenticacionService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
			JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authenticationManager) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.tokenRepository = tokenRepository;
		this.authenticationManager = authenticationManager;
	}

	public TokenResponse login(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
		String jwtToken = jwtService.generarToken(usuario);
		String refreshToken = jwtService.generarRefreshToken(usuario);
		revokeAllUserTokens(usuario);
		saveUserToken(usuario, jwtToken);
		return new TokenResponse(jwtToken, refreshToken);
	}

	public TokenResponse register(Usuario usuario) {
		// Verificar si el usuario ya existe
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioExistente.isPresent()) {
			throw new RuntimeException("El email ya está en uso.");
		}

		// Encriptar la contraseña antes de guardarla
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		Usuario usu = usuarioRepository.save(usuario);
		String jwtToken = jwtService.generarToken(usuario);
		String refreshToken = jwtService.generarRefreshToken(usuario);
		saveUserToken(usuario, jwtToken);
		return new TokenResponse(jwtToken, refreshToken);
	}
	
	public void logout(String token) {
		
	} 
public void revokeAllUserTokens(final Usuario usuario) {
		final List<Token> validUserTokens = tokenRepository.findByUsuarioIdUsuarioAndExpiredFalseAndRevokedFalse(usuario.getIdUsuario());
		if (!validUserTokens.isEmpty()) {
			for (final Token token : validUserTokens) {
				token.setExpired(true);
				token.setRevoked(true);
			}
			tokenRepository.saveAll(validUserTokens);
		}
	}
	
	public TokenResponse refreshToken (final String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Invalid Bearer Token");
		}
		
		final String refreshToken = authHeader.substring(7);
		final String userEmail = jwtService.extractUsername(refreshToken);
		
		if (userEmail == null) {
			throw new IllegalArgumentException("Invalid Refresh Token");
		}
		
		final Usuario user = usuarioRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException(userEmail));
		
		if (!jwtService.isTokenValid(refreshToken, user)) {
			throw new IllegalArgumentException("Invalid Refresh Token");
		}
		
		final String accessToken = jwtService.generarToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, accessToken);
		return new TokenResponse(accessToken, refreshToken);
		
		
	}	
	
	private void saveUserToken(Usuario usuario, String jwtToken) {
		Token token = new Token();
		token.setUsuario(usuario);
		token.setToken(jwtToken);
		token.setTokenType(Token.TokenType.BEARER);
		token.setExpired(false);
		token.setRevoked(false);
		tokenRepository.save(token);
	}

}
