package com.quipux.configuration;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.quipux.model.Token;
import com.quipux.model.Usuario;
import com.quipux.repository.TokenRepository;
import com.quipux.repository.UsuarioRepository;
import com.quipux.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final UsuarioRepository userRepository;
	private final TokenRepository tokenRepository;
	
	public JwtAuthFilter (UsuarioRepository userRepository, UserDetailsService userDetailsService, JwtService jwtService, TokenRepository tokenRepository) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().contains("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String jwtToken = authHeader.substring(7);
		final String userEmail = jwtService.extractUsername(jwtToken);
		if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
			return;
		}
		
		final Token token = tokenRepository.findByToken(jwtToken);
		if (token == null || token.isExpired() || token.isRevoked()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
		final Optional<Usuario> user = userRepository.findByEmail(userEmail);
		if (user.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final boolean isTokenValid = jwtService.isTokenValid(jwtToken, user.get());
		if (!isTokenValid) {
			return;
		}
		
		final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}
	
}
