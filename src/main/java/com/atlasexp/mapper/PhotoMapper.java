package com.atlasexp.mapper;

import com.atlasexp.dto.NewPhotoDTO;
import com.atlasexp.dto.PhotoDTO;
import com.atlasexp.model.Photo;
import com.atlasexp.model.Trip;
import com.atlasexp.model.User;

public class PhotoMapper {

    public static PhotoDTO toDTO(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setUrl(photo.getUrl());
        dto.setDescription(photo.getDescription());
        dto.setUserId(photo.getUser().getId());
        dto.setTripId(photo.getTrip().getId());
        dto.setLikes(0); // pode ser adaptado se likes forem calculados
        return dto;
    }

    public static Photo fromNewDTO(NewPhotoDTO dto, User user, Trip trip) {
        Photo photo = new Photo();
        photo.setUrl(dto.getUrl());
        photo.setDescription(dto.getDescription());
        photo.setUser(user);
        photo.setTrip(trip);
        return photo;
    }
}
