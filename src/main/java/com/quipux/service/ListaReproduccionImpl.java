package com.quipux.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quipux.model.ListaReproduccion;
import com.quipux.repository.ListaReproduccionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ListaReproduccionImpl implements ListaReproduccionService{

	private ListaReproduccionRepository listaRepository;
	
	public ListaReproduccionImpl(ListaReproduccionRepository listaRepository) {
		this.listaRepository = listaRepository;
	}
	
	
	@Override
	public ListaReproduccion addList(ListaReproduccion lista) {
		return listaRepository.save(lista);
		
	}


	@Override
	public List<ListaReproduccion> findAll() {
		return listaRepository.findAll();
	}


	@Override
	public List<ListaReproduccion> findByNombre(String nombre) {
		return listaRepository.findByNombre(nombre);
	}


	@Override
	public void deleteList(String nombre) {
		List<ListaReproduccion> listas = listaRepository.findByNombre(nombre);
	    
	    if (listas.isEmpty()) {
	        throw new EntityNotFoundException("La lista no existe");
	    }

	    listaRepository.deleteAll(listas);
	}

}
