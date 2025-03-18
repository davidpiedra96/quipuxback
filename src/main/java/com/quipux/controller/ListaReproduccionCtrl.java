package com.quipux.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quipux.model.ListaReproduccion;
import com.quipux.service.ListaReproduccionService;

@RestController
@RequestMapping("/api/listareproduccion")
public class ListaReproduccionCtrl {
	
	private final ListaReproduccionService service;

    public ListaReproduccionCtrl(ListaReproduccionService service) {
        this.service = service;
    }
	
    /**
     * Método que agrega una lista de reproduccion a la BD
     * @param lista
     * @return
     */
	@PostMapping("/lists")
	public ResponseEntity<?> AddList (@RequestBody ListaReproduccion lista) {
		if (lista == null || lista.getNombre() == null || lista.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de la lista no puede estar vacío o ser nulo.");
        }
		ListaReproduccion nuevaLista = service.addList(lista);
		String encodedName = URLEncoder.encode(nuevaLista.getNombre(), StandardCharsets.UTF_8);
        URI location = URI.create("/api/listareproduccion/lists/" + encodedName);
		return ResponseEntity.created(location).body(nuevaLista);
	}
	
	
	/**
	 * Método que obtiene las listas de la BD
	 * @return
	 */
	@GetMapping("/lists")
	public ResponseEntity<?> getLists () {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	/**
	 * Método que busca una lista por nombre
	 * @param listName
	 * @return
	 */
	@GetMapping("/lists/{listName}")
	public ResponseEntity<?> getListName (@PathVariable String listName) {
		List<ListaReproduccion> lista = service.findByNombre(listName);
		if (lista.isEmpty())  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista no existe");
		List<String> lDescripcion= lista.stream().map(l -> "Nombre: " + l.getNombre() + ", Descripcion: " + l.getDescripcion()).collect(Collectors.toList());
		return ResponseEntity.ok().body(lDescripcion);
	}
	
	/**
	 * Método que busca una lista por el nombre y la elimina
	 * @param listName
	 * @return
	 */
	@DeleteMapping("/lists/{listName}")
	public ResponseEntity<?> deleteListName (@PathVariable String listName) {
		try {
			service.deleteList(listName);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
