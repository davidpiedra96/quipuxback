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
import org.junit.jupiter.api.BeforeEach;


import com.quipux.model.Cancion;
import com.quipux.repository.CancionRepository;

public class CancionServiceImplTest {
	@Mock
	CancionRepository repository;
	
	@InjectMocks
	CancionServiceImpl service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void crearCancion() {
		Cancion cancion = new Cancion();
        cancion.setIdCancion(1L);
        cancion.setTitulo("Bohemian Rhapsody");
        when(repository.save(any(Cancion.class))).thenReturn(cancion);
        
        Cancion resultado = service.crearCancion(cancion);
        assertEquals(1L, resultado.getIdCancion());
        assertEquals("Bohemian Rhapsody", resultado.getTitulo());
        verify(repository, times(1)).save(cancion);
	}
	
	@Test
	void listarCanciones() {
		Cancion cancion1 = new Cancion();
		cancion1.setIdCancion(1L);
		cancion1.setTitulo("Bohemian Rhapsody");
        
        Cancion cancion2 = new Cancion();
        cancion2.setIdCancion(2L);
        cancion2.setTitulo("Iron Man");
        
        List<Cancion> lista = new ArrayList<>();
        lista.add(cancion1);
        lista.add(cancion2);
        
        when(repository.findAll()).thenReturn(lista);
        
        List<Cancion> test = service.listarCanciones();
        
        assertNotNull(test);
        assertEquals(test, lista);
        assertEquals(test.get(0).getTitulo(), cancion1.getTitulo());
        verify(repository, times(1)).findAll();
	}
	
}
