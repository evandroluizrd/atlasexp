package com.atlasexp.repository;

import com.atlasexp.model.PhotoLike;
import com.atlasexp.model.PhotoLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoLikeRepository extends JpaRepository<PhotoLike, PhotoLikeId> {

    // Buscar todos os likes de uma determinada foto
    List<PhotoLike> findByPhotoId(Long photoId);

    // Verificar se um usuário curtiu uma determinada foto
    boolean existsById(PhotoLikeId id);

    // Deletar like por ID composto
    void deleteById(PhotoLikeId id);

    // Contar likes de uma foto
    long countByPhotoId(Long photoId);
}
