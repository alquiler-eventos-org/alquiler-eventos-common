package com.alquilereventos.common.repository;

import com.alquilereventos.common.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Integer> {

    List<ChecklistItem> findByOrdenAlquilerDetalleId(Integer ordenAlquilerDetalleId);
}
