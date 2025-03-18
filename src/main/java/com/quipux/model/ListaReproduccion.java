package com.quipux.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "listareproduccion")
public class ListaReproduccion {
	
	// Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idListaReproduccion;
	private String nombre;
	private String descripcion;
	
	@ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
	
	@ManyToMany
    @JoinTable(
        name = "lista_cancion",
        joinColumns = @JoinColumn(name = "idListaReproduccion"),
        inverseJoinColumns = @JoinColumn(name = "idCancion")
    )
	private List<Cancion> lCanciones;
	
	// Constructores
	/**
	 * Constructor con todos los campos
	 * @param idListaReproduccion
	 * @param nombre
	 * @param descripcion
	 * @param lCanciones
	 */
	public ListaReproduccion(Long idListaReproduccion, String nombre, String descripcion, List<Cancion> lCanciones) {
		this.idListaReproduccion = idListaReproduccion;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.lCanciones = lCanciones;
	}
	
	/**
	 * Constructor con todos los campos excepto el idListaReproduccion
	 * @param nombre
	 * @param descripcion
	 * @param lCanciones
	 */
	public ListaReproduccion(String nombre, String descripcion, List<Cancion> lCanciones) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.lCanciones = lCanciones;
	}
	
	/**
	 * Constructor vacio
	 */
	public ListaReproduccion() {
	}

	// Getters y Setters
	public Long getIdListaReproduccion() {
		return idListaReproduccion;
	}

	public void setIdListaReproduccion(Long idListaReproduccion) {
		this.idListaReproduccion = idListaReproduccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Cancion> getlCanciones() {
		return lCanciones;
	}

	public void setlCanciones(List<Cancion> lCanciones) {
		this.lCanciones = lCanciones;
	}
	

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// Metodos
	@Override
	public String toString() {
		return "ListaReproduccion [idListaReproduccion=" + idListaReproduccion + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", lCanciones=" + lCanciones + "]";
	}
	
	
	
	
	
}
