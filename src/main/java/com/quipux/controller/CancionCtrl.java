package com.quipux.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quipux.model.Cancion;
import com.quipux.service.CancionService;

@RestController
@RequestMapping("/api/cancion")
public class CancionCtrl {
	
	private final CancionService service;
	public CancionCtrl (CancionService service) {
		this.service = service;}
	
	/**
	 * Método que recibe una Cancion y la crea en la BD
	 * @param cancion
	 * @return
	 */
	@PostMapping("/crear")
	public ResponseEntity<?> crearCancion(@RequestBody Cancion cancion) {
		return ResponseEntity.ok().body(service.crearCancion(cancion));
	}
	
	/**
	 * Método que lista las Canciones de la BD
	 * @return
	 */
	@GetMapping("/listar")
	public ResponseEntity<?> listarCanciones() {
		return ResponseEntity.ok().body(service.listarCanciones());
	}
}
