package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

    List<OrdenCompra> findByProveedorId(Integer proveedorId);
}
