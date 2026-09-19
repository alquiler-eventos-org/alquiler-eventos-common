package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.OrdenAlquiler;
import com.alquilereventos.common.entity.enums.EstadoOrdenAlquiler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrdenAlquilerRepository extends JpaRepository<OrdenAlquiler, Integer> {

    /**
     * Busqueda con filtros opcionales y paginacion.
     */
    @Query("""
            SELECT o FROM OrdenAlquiler o
            WHERE (:estado IS NULL OR o.estado = :estado)
              AND (:clienteId IS NULL OR o.cliente.id = :clienteId)
              AND (:fechaDesde IS NULL OR o.fechaEvento >= :fechaDesde)
              AND (:fechaHasta IS NULL OR o.fechaEvento <= :fechaHasta)
            """)
    Page<OrdenAlquiler> buscar(@Param("estado") EstadoOrdenAlquiler estado,
                               @Param("clienteId") Integer clienteId,
                               @Param("fechaDesde") LocalDate fechaDesde,
                               @Param("fechaHasta") LocalDate fechaHasta,
                               Pageable pageable);
}
