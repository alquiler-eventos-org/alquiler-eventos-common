package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenCompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenCompraDetalleRepository extends JpaRepository<OrdenCompraDetalle, Integer> {

    List<OrdenCompraDetalle> findByOrdenCompraId(Integer ordenCompraId);
}
