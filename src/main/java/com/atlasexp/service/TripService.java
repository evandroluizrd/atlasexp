package com.atlasexp.service;

import com.atlasexp.dto.TripDTO;
import com.atlasexp.mapper.TripMapper;
import com.atlasexp.model.Trip;
import com.atlasexp.model.User;
import com.atlasexp.repository.TripRepository;
import com.atlasexp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripService {

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(TripMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TripDTO> getTripById(Long id) {
        return tripRepository.findById(id)
                .map(TripMapper::toDTO);
    }

    @Transactional
    public TripDTO createTrip(TripDTO tripDTO) {
        Optional<User> userOpt = userRepository.findById(tripDTO.getUserId());

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + tripDTO.getUserId());
        }

        Trip trip = TripMapper.toEntity(tripDTO, userOpt.get());
        Trip saved = tripRepository.save(trip);

        // Log de auditoria
        auditLogService.log("CREATE", "Trip", saved.getId(), null);

        return TripMapper.toDTO(saved);
    }

    @Transactional
    public TripDTO updateTrip(Long id, TripDTO tripDTO) {
        Optional<Trip> optional = tripRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(tripDTO.getUserId());

        if (optional.isPresent() && userOpt.isPresent()) {
            Trip updated = TripMapper.toEntity(tripDTO, userOpt.get());
            updated.setId(id);
            Trip saved = tripRepository.save(updated);

            // Log de auditoria
            auditLogService.log("UPDATE", "Trip", saved.getId(), null);

            return TripMapper.toDTO(saved);
        }

        return null;
    }

    @Transactional
    public boolean deleteTrip(Long id) {
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);

            // Log de auditoria
            auditLogService.log("DELETE", "Trip", id, null);

            return true;
        }
        return false;
    }
}
