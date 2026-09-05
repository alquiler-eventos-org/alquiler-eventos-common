package com.alquilereventos.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    private String telefono;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String documento;

    private String direccion;

    @Column(columnDefinition = "TEXT")
    private String notas;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<OrdenAlquiler> ordenesAlquiler;
}
