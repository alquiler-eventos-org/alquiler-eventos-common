package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenCompraHistorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenCompraHistorialRepository extends JpaRepository<OrdenCompraHistorial, Integer> {

    List<OrdenCompraHistorial> findByOrdenCompraId(Integer ordenCompraId);
}
