package com.quipux.service;

import java.util.List;

import com.quipux.model.ListaReproduccion;

public interface ListaReproduccionService {
	ListaReproduccion addList(ListaReproduccion lista);
	List<ListaReproduccion> findAll();
	List<ListaReproduccion> findByNombre(String nombre);
	void deleteList(String nombre);
}
