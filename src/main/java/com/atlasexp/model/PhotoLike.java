package com.atlasexp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "photo_likes")
public class PhotoLike {

    @EmbeddedId
    private PhotoLikeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("photoId")
    @JoinColumn(name = "photo_id")
    private Photo photo;

    private LocalDateTime likedAt;

    // Construtores
    public PhotoLike() {}

    public PhotoLike(User user, Photo photo) {
        this.user = user;
        this.photo = photo;
        this.id = new PhotoLikeId(user.getId(), photo.getId());
        this.likedAt = LocalDateTime.now();
    }

    // Getters e Setters
    public PhotoLikeId getId() { return id; }
    public void setId(PhotoLikeId id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Photo getPhoto() { return photo; }
    public void setPhoto(Photo photo) { this.photo = photo; }

    public LocalDateTime getLikedAt() { return likedAt; }
    public void setLikedAt(LocalDateTime likedAt) { this.likedAt = likedAt; }
}
