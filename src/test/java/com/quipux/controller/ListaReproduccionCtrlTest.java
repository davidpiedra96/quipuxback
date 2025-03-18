package com.quipux.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quipux.model.Cancion;
import com.quipux.model.ListaReproduccion;
import com.quipux.service.ListaReproduccionService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ListaReproduccionCtrlTest {

	private MockMvc mockMvc;
	
	@Mock
    private ListaReproduccionService service;
    
    @InjectMocks
    private ListaReproduccionCtrl controller;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    @Test
    void testAddList1() throws Exception {
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
        
        
        when(service.addList(any(ListaReproduccion.class))).thenReturn(lista);
        
        mockMvc.perform(post("/api/listareproduccion/lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lista)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Lista Test 1"));
    }
    
    @Test
    void testAddList2() throws Exception {
        mockMvc.perform(post("/api/listareproduccion/lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El nombre de la lista no puede estar vacío o ser nulo."));
    }
    
    @Test
    void testGetLists1() throws Exception {
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
        
        when(service.findAll()).thenReturn(l);
        
        mockMvc.perform(get("/api/listareproduccion/lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
    
    @Test
    void testGetListName2() throws Exception {
        when(service.findByNombre("Lista Inexistente")).thenReturn(Collections.emptyList());
        
        mockMvc.perform(get("/api/listareproduccion/lists/Lista%20Inexistente"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La lista no existe"));
    }
    
    @Test
    void testDelete1() throws Exception {
        doNothing().when(service).deleteList("Lista 1");
        
        mockMvc.perform(delete("/api/listareproduccion/lists/Lista%201"))
                .andExpect(status().isNoContent());
    }
    
}
