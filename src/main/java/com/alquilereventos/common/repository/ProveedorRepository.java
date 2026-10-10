package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    Optional<Proveedor> findByNombre(String nombre);

    /**
     * Busqueda con filtros opcionales y paginacion.
     * Excluye siempre los proveedores con borrado logico (activo = false).
     *
     * Los filtros se CASTean a text: sin el cast, Hibernate envia el NULL como
     * bytea y PostgreSQL falla con "function lower(bytea) does not exist".
     */
    @Query("""
            SELECT p FROM Proveedor p
            WHERE p.activo = true
              AND (CAST(:nombre AS text) IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', CAST(:nombre AS text), '%')))
              AND (CAST(:email AS text) IS NULL OR LOWER(p.email) LIKE LOWER(CONCAT('%', CAST(:email AS text), '%')))
              AND (CAST(:contacto AS text) IS NULL OR LOWER(p.contacto) LIKE LOWER(CONCAT('%', CAST(:contacto AS text), '%')))
            """)
    Page<Proveedor> buscar(@Param("nombre") String nombre,
                           @Param("email") String email,
                           @Param("contacto") String contacto,
                           Pageable pageable);
}
