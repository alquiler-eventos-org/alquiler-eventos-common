package com.alquilereventos.common.entity;

import com.alquilereventos.common.entity.enums.EstadoEquipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "equipos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equipo_categoria"))
    private CategoriaEquipo categoria;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioDia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEquipo estado = EstadoEquipo.DISPONIBLE;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY)
    private List<OrdenAlquilerDetalle> ordenesAlquilerDetalle;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY)
    private List<OrdenCompraDetalle> ordenesCompraDetalle;
}
