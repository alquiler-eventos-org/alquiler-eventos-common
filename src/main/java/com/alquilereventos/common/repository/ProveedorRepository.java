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
     */
    @Query("""
            SELECT p FROM Proveedor p
            WHERE p.activo = true
              AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
              AND (:email IS NULL OR LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%')))
              AND (:contacto IS NULL OR LOWER(p.contacto) LIKE LOWER(CONCAT('%', :contacto, '%')))
            """)
    Page<Proveedor> buscar(@Param("nombre") String nombre,
                           @Param("email") String email,
                           @Param("contacto") String contacto,
                           Pageable pageable);
}
