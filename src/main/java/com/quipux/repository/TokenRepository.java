package com.quipux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quipux.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	List<Token> findByUsuarioIdUsuarioAndExpiredFalseAndRevokedFalse(long idUsuario);
	Token findByToken(String token);
}
