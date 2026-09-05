package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, Integer> {

    List<RolPermiso> findByRolId(Integer rolId);
}
