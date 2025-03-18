package com.quipux.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quipux.model.Cancion;
import com.quipux.repository.CancionRepository;

@Service
public class CancionServiceImpl implements CancionService{

	private final CancionRepository repository;
	public CancionServiceImpl(CancionRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Cancion crearCancion(Cancion cancion) {
		return repository.save(cancion);
	}

	@Override
	public List<Cancion> listarCanciones() {
		return repository.findAll();
	}

}
