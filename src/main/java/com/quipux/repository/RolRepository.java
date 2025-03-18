package com.quipux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quipux.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
