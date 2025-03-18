package com.quipux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quipux.model.Cancion;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long>{

}
