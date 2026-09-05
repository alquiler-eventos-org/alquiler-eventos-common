package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenAlquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenAlquilerRepository extends JpaRepository<OrdenAlquiler, Integer> {

    List<OrdenAlquiler> findByClienteId(Integer clienteId);

    List<OrdenAlquiler> findByEstado(String estado);
}
