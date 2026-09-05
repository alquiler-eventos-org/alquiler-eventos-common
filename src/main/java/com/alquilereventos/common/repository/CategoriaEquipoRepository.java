package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.CategoriaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaEquipoRepository extends JpaRepository<CategoriaEquipo, Integer> {

    Optional<CategoriaEquipo> findByNombre(String nombre);
}
