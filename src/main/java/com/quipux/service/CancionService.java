package com.quipux.service;

import java.util.List;

import com.quipux.model.Cancion;

public interface CancionService {
	Cancion crearCancion(Cancion cancion);
	List<Cancion> listarCanciones();
	
}
