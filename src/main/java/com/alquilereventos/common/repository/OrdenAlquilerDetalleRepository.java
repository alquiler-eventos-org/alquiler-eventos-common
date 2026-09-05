package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenAlquilerDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenAlquilerDetalleRepository extends JpaRepository<OrdenAlquilerDetalle, Integer> {

    List<OrdenAlquilerDetalle> findByOrdenAlquilerId(Integer ordenAlquilerId);
}
