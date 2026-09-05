package com.alquilereventos.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Clase abstracta base que modela el historial de cambios de estado de una
 * orden (de alquiler o de compra).
 *
 * <p>Esta clase materializa los cuatro pilares de la programacion orientada
 * a objetos que se solicitan en la entrega:</p>
 * <ul>
 *   <li><b>Abstraccion:</b> {@link HistorialEstado} define un contrato generico
 *       (identificador, usuario que realizo el cambio, estado anterior, estado
 *       nuevo y fecha) omitiendo los detalles particulares de cada tipo de orden.</li>
 *   <li><b>Herencia:</b> gracias a {@link jakarta.persistence.MappedSuperclass},
 *       las subclases {@link OrdenAlquilerHistorial} y {@link OrdenCompraHistorial}
 *       heredan todos los campos y el comportamiento comun, y agregan sus
 *       atributos especificos.</li>
 *   <li><b>Encapsulacion:</b> el acceso a los campos es privado y se realiza
 *       unicamente a traves de los getters y setters generados por Lombok
 *       (anotaciones {@code @Getter} y {@code @Setter}).</li>
 *   <li><b>Polimorfismo:</b> la superclase define {@link #onCreate()} como un
 *       metodo protegido de ciclo de vida que las subclases heredan, y cualquier
 *       objeto de las subclases puede tratarse como un {@link HistorialEstado}.</li>
 * </ul>
 *
 * <p>La superclase no se mapea directamente a una tabla, sino que sus columnas
 * se incorporan a las tablas de las subclases concretas ({@code @MappedSuperclass}).</p>
 *
 * @see OrdenAlquilerHistorial
 * @see OrdenCompraHistorial
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class HistorialEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_historial_usuario"))
    private Usuario usuario;

    @Column(name = "estado_anterior", nullable = false)
    private String estadoAnterior;

    @Column(name = "estado_nuevo", nullable = false)
    private String estadoNuevo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}
