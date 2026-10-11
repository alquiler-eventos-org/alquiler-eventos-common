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
     *
     * Los filtros se CASTean a text: sin el cast, Hibernate envia el NULL como
     * bytea y PostgreSQL falla con "function lower(bytea) does not exist".
     */
    @Query("""
            SELECT c FROM Cliente c
            WHERE c.activo = true
              AND (CAST(:nombre AS text) IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', CAST(:nombre AS text), '%')))
              AND (CAST(:apellido AS text) IS NULL OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', CAST(:apellido AS text), '%')))
              AND (CAST(:documento AS text) IS NULL OR c.documento LIKE CONCAT('%', CAST(:documento AS text), '%'))
              AND (CAST(:email AS text) IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', CAST(:email AS text), '%')))
            """)
    Page<Cliente> buscar(@Param("nombre") String nombre,
                         @Param("apellido") String apellido,
                         @Param("documento") String documento,
                         @Param("email") String email,
                         Pageable pageable);
}
