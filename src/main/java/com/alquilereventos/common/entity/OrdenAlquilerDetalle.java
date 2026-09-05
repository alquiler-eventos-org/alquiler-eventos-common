package com.alquilereventos.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ordenes_alquiler_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenAlquilerDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_alquiler_id", nullable = false, foreignKey = @ForeignKey(name = "fk_detalle_alquiler_orden"))
    private OrdenAlquiler ordenAlquiler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false, foreignKey = @ForeignKey(name = "fk_detalle_alquiler_equipo"))
    private Equipo equipo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @OneToMany(mappedBy = "ordenAlquilerDetalle", fetch = FetchType.LAZY)
    private java.util.List<ChecklistItem> checklistItems;
}
