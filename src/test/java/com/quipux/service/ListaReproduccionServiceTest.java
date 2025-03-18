package com.quipux.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.quipux.model.Cancion;
import com.quipux.model.ListaReproduccion;
import com.quipux.repository.ListaReproduccionRepository;



import org.junit.jupiter.api.BeforeEach;

public class ListaReproduccionServiceTest {

	@Mock
	ListaReproduccionRepository repository;
	
	@InjectMocks
	ListaReproduccionImpl service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void addList() {
		ListaReproduccion lista = new ListaReproduccion();
		lista.setIdListaReproduccion(1L);
		lista.setNombre("Lista Test 1");
		lista.setDescripcion("Descripcion lista test 1");
		Cancion cancion = new Cancion();
        cancion.setIdCancion(1L);
        cancion.setTitulo("Bohemian Rhapsody");
        List<Cancion> lCanciones = new ArrayList<>();
        lCanciones.add(cancion);
        lista.setlCanciones(lCanciones);
        
        when(repository.save(any(ListaReproduccion.class))).thenReturn(lista);
        
        ListaReproduccion test = service.addList(lista);
        
        assertNotNull(test);
        assertEquals(test, lista);
        assertEquals(test.getlCanciones().size(), lista.getlCanciones().size());
        verify(repository,times(1)).save(any(ListaReproduccion.class));
		
	}
	
	@Test 
	void findAll() {
		ListaReproduccion lista = new ListaReproduccion();
		lista.setIdListaReproduccion(1L);
		lista.setNombre("Lista Test 1");
		lista.setDescripcion("Descripcion lista test 1");
		Cancion cancion = new Cancion();
        cancion.setIdCancion(1L);
        cancion.setTitulo("Bohemian Rhapsody");
        List<Cancion> lCanciones = new ArrayList<>();
        lCanciones.add(cancion);
        lista.setlCanciones(lCanciones);
        List<ListaReproduccion> l = new ArrayList<>();
        l.add(lista);
        when(repository.findAll()).thenReturn(l);
        
        List<ListaReproduccion> test = service.findAll();
        assertNotNull(test);
        assertEquals(test, l);
        assertEquals(test.get(0), l.get(0));
        verify(repository,times(1)).findAll();
        
        
        
	}
	
	@Test
	void findByNombre() {
		ListaReproduccion lista = new ListaReproduccion();
		lista.setIdListaReproduccion(1L);
		lista.setNombre("Lista Test 1");
		lista.setDescripcion("Descripcion lista test 1");
		String nombre = "Lista Test 1";
		List<ListaReproduccion> l = new ArrayList<>();
        l.add(lista);
		when(repository.findByNombre(nombre)).thenReturn(l);
		
		List<ListaReproduccion> test = service.findByNombre(nombre);
		assertNotNull(test);
        assertEquals(test, l);
        assertEquals(test.get(0), l.get(0));
        verify(repository,times(1)).findByNombre(nombre);
	}
	
	@Test
	void deleteList() {
		ListaReproduccion lista = new ListaReproduccion();
		lista.setIdListaReproduccion(1L);
		lista.setNombre("Lista Test 1");
		lista.setDescripcion("Descripcion lista test 1");
		String nombre = "Lista Test 1";
		List<ListaReproduccion> l = new ArrayList<>();
        l.add(lista);
        when(repository.findByNombre(nombre)).thenReturn(l);
        doNothing().when(repository).deleteAll(any());
        
        service.deleteList(nombre);
        verify(repository, times(1)).findByNombre(nombre);
        verify(repository, times(1)).deleteAll(l);
	}
	
	
	
	
}
