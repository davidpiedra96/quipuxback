package com.quipux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.quipux.service.LastFMService;

@RestController
@RequestMapping("/api/generosFM")
public class GenerosFMCtrl {
	
	private final LastFMService lastFmService;
	
	public GenerosFMCtrl(LastFMService lastFmService) {
        this.lastFmService = lastFmService;
    }

	/**
	 * Método que obtiene una lista de Generos de la api de FM
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
    @GetMapping
    public ResponseEntity<?> getGeneros() throws JsonMappingException, JsonProcessingException {
        return ResponseEntity.ok(lastFmService.getGenres());
    }
}
