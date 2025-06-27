package com.atlasexp.controller;

import com.atlasexp.dto.NewPhotoDTO;
import com.atlasexp.dto.PhotoDTO;
import com.atlasexp.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping
    public ResponseEntity<PhotoDTO> createPhoto(@Valid @RequestBody NewPhotoDTO dto) {
        PhotoDTO created = photoService.save(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<PhotoDTO>> getAllPhotos() {
        return ResponseEntity.ok(photoService.getAll());
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByTrip(@PathVariable Long tripId) {
        return ResponseEntity.ok(photoService.getByTrip(tripId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(photoService.getByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        boolean deleted = photoService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ✅ Curtir uma foto
    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePhoto(@PathVariable Long id, @RequestParam Long userId) {
        boolean liked = photoService.likePhoto(id, userId);
        if (liked) {
            return ResponseEntity.ok("Foto curtida com sucesso!");
        }
        return ResponseEntity.badRequest().body("Não foi possível curtir a foto (usuário inválido, duplicado ou auto-like).");
    }

    // ✅ Descurtir uma foto
    @DeleteMapping("/{id}/like")
    public ResponseEntity<String> unlikePhoto(@PathVariable Long id, @RequestParam Long userId) {
        boolean unliked = photoService.unlikePhoto(id, userId);
        if (unliked) {
            return ResponseEntity.ok("Curtida removida com sucesso.");
        }
        return ResponseEntity.badRequest().body("Não foi possível remover curtida (curtida inexistente).");
    }

    // ✅ Contar curtidas de uma foto
    @GetMapping("/{id}/likes")
    public ResponseEntity<Long> countLikes(@PathVariable Long id) {
        long count = photoService.countLikes(id);
        return ResponseEntity.ok(count);
    }
}
