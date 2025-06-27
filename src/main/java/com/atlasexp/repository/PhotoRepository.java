package com.atlasexp.repository;

import com.atlasexp.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    // Buscar todas as fotos de uma viagem específica
    List<Photo> findByTripId(Long tripId);

    // Buscar todas as fotos de um usuário específico
    List<Photo> findByUserId(Long userId);
}
