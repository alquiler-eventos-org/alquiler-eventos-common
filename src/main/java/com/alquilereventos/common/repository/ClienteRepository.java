package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDocumento(String documento);

    Optional<Cliente> findByEmail(String email);
}
