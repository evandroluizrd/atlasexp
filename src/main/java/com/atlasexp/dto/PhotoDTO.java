package com.atlasexp.dto;

public class PhotoDTO {
    private Long id;
    private String url;
    private String description;
    private Long userId;
    private Long tripId;
    private int likes;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
}
