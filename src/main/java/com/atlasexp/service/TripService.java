package com.atlasexp.service;

import com.atlasexp.model.Trip;
import com.atlasexp.model.User;
import com.atlasexp.repository.TripRepository;
import com.atlasexp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }

    public Trip createTrip(Trip trip) {
        // Verifica se o usuário existe
        if (trip.getUser() != null && trip.getUser().getId() != null) {
            Optional<User> userOptional = userRepository.findById(trip.getUser().getId());
            if (userOptional.isPresent()) {
                trip.setUser(userOptional.get());
            } else {
                throw new IllegalArgumentException("Usuário com ID " + trip.getUser().getId() + " não encontrado.");
            }
        } else {
            throw new IllegalArgumentException("Usuário é obrigatório para criar uma viagem.");
        }

        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long id, Trip updatedTrip) {
        return tripRepository.findById(id).map(trip -> {
            trip.setTitle(updatedTrip.getTitle());
            trip.setDestination(updatedTrip.getDestination());
            trip.setStartDate(updatedTrip.getStartDate());
            trip.setEndDate(updatedTrip.getEndDate());

            if (updatedTrip.getUser() != null && updatedTrip.getUser().getId() != null) {
                Optional<User> userOptional = userRepository.findById(updatedTrip.getUser().getId());
                userOptional.ifPresent(trip::setUser);
            }

            return tripRepository.save(trip);
        }).orElse(null);
    }

    public boolean deleteTrip(Long id) {
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
