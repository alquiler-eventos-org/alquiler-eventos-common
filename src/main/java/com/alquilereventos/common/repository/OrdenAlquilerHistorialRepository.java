package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenAlquilerHistorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenAlquilerHistorialRepository extends JpaRepository<OrdenAlquilerHistorial, Integer> {

    List<OrdenAlquilerHistorial> findByOrdenAlquilerId(Integer ordenAlquilerId);
}
