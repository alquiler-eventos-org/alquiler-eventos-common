package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {

    Optional<Configuracion> findByClave(String clave);
}
