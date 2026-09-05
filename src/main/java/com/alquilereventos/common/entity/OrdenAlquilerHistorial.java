package com.alquilereventos.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Historial de cambios de estado de una orden de alquiler.
 *
 * <p>Hereda los campos comunes de {@link HistorialEstado} (abstraccion y
 * herencia) y agrega la referencia a la {@link OrdenAlquiler} a la que
 * pertenece y el monto de mora generado.</p>
 *
 * @see HistorialEstado
 */
@Entity
@Table(name = "ordenes_alquiler_historial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenAlquilerHistorial extends HistorialEstado {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_alquiler_id", nullable = false, foreignKey = @ForeignKey(name = "fk_historial_alquiler_orden"))
    private OrdenAlquiler ordenAlquiler;

    @Column(name = "monto_mora", precision = 10, scale = 2)
    private BigDecimal montoMora;
}
