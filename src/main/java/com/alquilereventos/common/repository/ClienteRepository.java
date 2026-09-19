package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDocumento(String documento);

    Optional<Cliente> findByEmail(String email);

    /**
     * Busqueda con filtros opcionales y paginacion.
     * Excluye siempre los clientes con borrado logico (activo = false).
     */
    @Query("""
            SELECT c FROM Cliente c
            WHERE c.activo = true
              AND (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
              AND (:apellido IS NULL OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :apellido, '%')))
              AND (:documento IS NULL OR c.documento LIKE CONCAT('%', :documento, '%'))
              AND (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))
            """)
    Page<Cliente> buscar(@Param("nombre") String nombre,
                         @Param("apellido") String apellido,
                         @Param("documento") String documento,
                         @Param("email") String email,
                         Pageable pageable);
}
