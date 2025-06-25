package com.atlasexp.mapper;

import com.atlasexp.dto.TripDTO;
import com.atlasexp.model.Trip;
import com.atlasexp.model.User;

public class TripMapper {

    public static TripDTO toDTO(Trip trip) {
        TripDTO dto = new TripDTO();
        dto.setId(trip.getId());
        dto.setTitle(trip.getTitle());
        dto.setDestination(trip.getDestination());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setUserId(trip.getUser() != null ? trip.getUser().getId() : null);
        return dto;
    }

    public static Trip toEntity(TripDTO dto, User user) {
        Trip trip = new Trip();
        trip.setId(dto.getId());
        trip.setTitle(dto.getTitle());
        trip.setDestination(dto.getDestination());
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        trip.setUser(user);
        return trip;
    }
}
