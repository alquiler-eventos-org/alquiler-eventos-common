package com.alquilereventos.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Historial de cambios de estado de una orden de compra.
 *
 * <p>Hereda los campos comunes de {@link HistorialEstado} (abstraccion y
 * herencia) y agrega la referencia a la {@link OrdenCompra} a la que pertenece.</p>
 *
 * @see HistorialEstado
 */
@Entity
@Table(name = "ordenes_compra_historial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompraHistorial extends HistorialEstado {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_compra_id", nullable = false, foreignKey = @ForeignKey(name = "fk_historial_compra_orden"))
    private OrdenCompra ordenCompra;
}
