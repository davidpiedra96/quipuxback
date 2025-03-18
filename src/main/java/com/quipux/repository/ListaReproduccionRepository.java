package com.quipux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quipux.model.ListaReproduccion;
import java.util.List;


@Repository
public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion, Long>{
	List<ListaReproduccion> findByNombre(String nombre);
}
