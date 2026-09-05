package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    List<Equipo> findByCategoriaId(Integer categoriaId);
}
