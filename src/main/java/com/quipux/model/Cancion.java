package com.quipux.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cancion")
public class Cancion {
	// Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCancion;
	private String titulo;
	private String artista;
	private String album;
	private String anno;
	private String genero;
	
	@ManyToMany
	private List<ListaReproduccion> listas;

	// Constructor
	/**
	 * Constructor vacio
	 */
	public Cancion() {
	}

	/**
	 * Constructor con todos los campos
	 * 
	 * @param idCancion
	 * @param titulo
	 * @param artista
	 * @param album
	 * @param anno
	 * @param genero
	 */
	public Cancion(Long idCancion, String titulo, String artista, String album, String anno, String genero) {
		this.idCancion = idCancion;
		this.titulo = titulo;
		this.artista = artista;
		this.album = album;
		this.anno = anno;
		this.genero = genero;
	}

	/**
	 * Constructor con todos los campos excepto el idCancion
	 * 
	 * @param titulo
	 * @param artista
	 * @param album
	 * @param anno
	 * @param genero
	 */
	public Cancion(String titulo, String artista, String album, String anno, String genero) {
		this.titulo = titulo;
		this.artista = artista;
		this.album = album;
		this.anno = anno;
		this.genero = genero;
	}

	// Getters y Setters
	public String getTitulo() {
		return titulo;
	}

	public Long getIdCancion() {
		return idCancion;
	}

	public void setIdCancion(Long idCancion) {
		this.idCancion = idCancion;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	// Métodos
	@Override
	public String toString() {
		return "Cancion [idCancion=" + idCancion + ", titulo=" + titulo + ", artista=" + artista + ", album=" + album
				+ ", anno=" + anno + ", genero=" + genero + "]";
	}
}
