package com.atlasexp.mapper;

import com.atlasexp.dto.ActivityDTO;
import com.atlasexp.model.Activity;
import com.atlasexp.model.Trip;

public class ActivityMapper {

    public static ActivityDTO toDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setDescription(activity.getDescription());
        dto.setDateTime(activity.getDateTime());
        dto.setTripId(activity.getTrip() != null ? activity.getTrip().getId() : null);
        return dto;
    }

    public static Activity toEntity(ActivityDTO dto, Trip trip) {
        Activity activity = new Activity();
        activity.setId(dto.getId());
        activity.setName(dto.getName());
        activity.setDescription(dto.getDescription());
        activity.setDateTime(dto.getDateTime());
        activity.setTrip(trip);
        return activity;
    }
}
