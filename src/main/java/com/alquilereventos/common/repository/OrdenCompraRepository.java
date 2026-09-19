package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenCompra;
import com.alquilereventos.common.entity.enums.EstadoOrdenCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

    /**
     * Busqueda con filtros opcionales y paginacion.
     */
    @Query("""
            SELECT o FROM OrdenCompra o
            WHERE (:estado IS NULL OR o.estado = :estado)
              AND (:proveedorId IS NULL OR o.proveedor.id = :proveedorId)
              AND (:fechaDesde IS NULL OR o.fechaCompra >= :fechaDesde)
              AND (:fechaHasta IS NULL OR o.fechaCompra <= :fechaHasta)
            """)
    Page<OrdenCompra> buscar(@Param("estado") EstadoOrdenCompra estado,
                             @Param("proveedorId") Integer proveedorId,
                             @Param("fechaDesde") LocalDate fechaDesde,
                             @Param("fechaHasta") LocalDate fechaHasta,
                             Pageable pageable);
}
