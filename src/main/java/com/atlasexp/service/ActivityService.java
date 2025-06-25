package com.atlasexp.service;

import com.atlasexp.dto.ActivityDTO;
import com.atlasexp.mapper.ActivityMapper;
import com.atlasexp.model.Activity;
import com.atlasexp.model.Trip;
import com.atlasexp.repository.ActivityRepository;
import com.atlasexp.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TripRepository tripRepository;

    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(ActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ActivityDTO> getActivityById(Long id) {
        return activityRepository.findById(id).map(ActivityMapper::toDTO);
    }

    @Transactional
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        Optional<Trip> tripOpt = tripRepository.findById(activityDTO.getTripId());

        if (tripOpt.isEmpty()) {
            throw new IllegalArgumentException("Trip não encontrada para ID: " + activityDTO.getTripId());
        }

        Activity activity = ActivityMapper.toEntity(activityDTO, tripOpt.get());
        Activity saved = activityRepository.save(activity);
        return ActivityMapper.toDTO(saved);
    }

    @Transactional
    public ActivityDTO updateActivity(Long id, ActivityDTO activityDTO) {
        Optional<Activity> optional = activityRepository.findById(id);
        Optional<Trip> tripOpt = tripRepository.findById(activityDTO.getTripId());

        if (optional.isPresent() && tripOpt.isPresent()) {
            Activity updated = ActivityMapper.toEntity(activityDTO, tripOpt.get());
            updated.setId(id); // manter o mesmo ID
            return ActivityMapper.toDTO(activityRepository.save(updated));
        }

        return null;
    }

    @Transactional
    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
