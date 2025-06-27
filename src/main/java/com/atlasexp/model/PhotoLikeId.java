package com.atlasexp.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PhotoLikeId implements Serializable {

    private Long userId;
    private Long photoId;

    // Construtores
    public PhotoLikeId() {}
    public PhotoLikeId(Long userId, Long photoId) {
        this.userId = userId;
        this.photoId = photoId;
    }

    // Getters e Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPhotoId() { return photoId; }
    public void setPhotoId(Long photoId) { this.photoId = photoId; }

    // equals e hashCode são obrigatórios para IDs compostos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotoLikeId)) return false;
        PhotoLikeId that = (PhotoLikeId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(photoId, that.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, photoId);
    }
}
