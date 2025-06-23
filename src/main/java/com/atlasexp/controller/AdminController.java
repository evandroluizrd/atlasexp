package com.atlasexp.controller;

import com.atlasexp.repository.ActivityRepository;
import com.atlasexp.repository.TripRepository;
import com.atlasexp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/reset")
    public ResponseEntity<String> resetDatabase() {
        // Ordem correta para evitar erro de chave estrangeira
        activityRepository.deleteAll(); // Atividades dependem de viagens
        tripRepository.deleteAll();     // Viagens dependem de usuários
        userRepository.deleteAll();     // Usuários

        return ResponseEntity.ok("Dados do banco resetados com sucesso.");
    }
}
