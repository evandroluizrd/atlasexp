package com.atlasexp.service;

import com.atlasexp.dto.NewPhotoDTO;
import com.atlasexp.dto.PhotoDTO;
import com.atlasexp.mapper.PhotoMapper;
import com.atlasexp.model.*;
import com.atlasexp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PhotoLikeRepository photoLikeRepository;

    @Autowired
    private AuditLogService auditLogService;

    public PhotoDTO save(NewPhotoDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("Usuário não encontrado"));

        Trip trip = tripRepository.findById(dto.getTripId()).orElseThrow(() ->
                new IllegalArgumentException("Viagem não encontrada"));

        Photo photo = PhotoMapper.fromNewDTO(dto, user, trip);
        Photo saved = photoRepository.save(photo);

        auditLogService.log("CREATE", "Photo", saved.getId(), null);

        return PhotoMapper.toDTO(saved);
    }

    public List<PhotoDTO> getAll() {
        return photoRepository.findAll()
                .stream()
                .map(PhotoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PhotoDTO> getByTrip(Long tripId) {
        return photoRepository.findByTripId(tripId)
                .stream()
                .map(PhotoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PhotoDTO> getByUser(Long userId) {
        return photoRepository.findByUserId(userId)
                .stream()
                .map(PhotoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<Photo> photoOpt = photoRepository.findById(id);
        if (photoOpt.isPresent()) {
            photoRepository.delete(photoOpt.get());

            auditLogService.log("DELETE", "Photo", id, null);

            return true;
        }
        return false;
    }

    public boolean likePhoto(Long photoId, Long userId) {
        if (userId == null || photoId == null) return false;

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Photo> photoOpt = photoRepository.findById(photoId);

        if (userOpt.isEmpty() || photoOpt.isEmpty()) return false;

        Photo photo = photoOpt.get();
        User user = userOpt.get();

        if (photo.getUser().getId().equals(userId)) return false;

        PhotoLikeId likeId = new PhotoLikeId(userId, photoId);
        if (photoLikeRepository.existsById(likeId)) return false;

        PhotoLike like = new PhotoLike(user, photo);
        photoLikeRepository.save(like);

        auditLogService.log("LIKE", "Photo", photoId, user.getName());

        return true;
    }

    public boolean unlikePhoto(Long photoId, Long userId) {
        PhotoLikeId likeId = new PhotoLikeId(userId, photoId);

        if (!photoLikeRepository.existsById(likeId)) return false;

        photoLikeRepository.deleteById(likeId);

        Optional<User> userOpt = userRepository.findById(userId);
        userOpt.ifPresent(user ->
                auditLogService.log("UNLIKE", "Photo", photoId, user.getName())
        );

        return true;
    }

    public long countLikes(Long photoId) {
        return photoLikeRepository.countByPhotoId(photoId);
    }
}
