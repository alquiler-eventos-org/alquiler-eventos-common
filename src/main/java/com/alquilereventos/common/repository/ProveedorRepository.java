package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    Optional<Proveedor> findByNombre(String nombre);
}
