package com.atlasexp.service;

import com.atlasexp.dto.UserDTO;
import com.atlasexp.mapper.UserMapper;
import com.atlasexp.model.User;
import com.atlasexp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogService auditLogService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPremium(userDTO.isPremium());
        User saved = userRepository.save(user);

        auditLogService.log("CREATE", "User", saved.getId(), null);

        return UserMapper.toDTO(saved);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());

            if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            user.setPremium(userDTO.isPremium());
            User updated = userRepository.save(user);

            auditLogService.log("UPDATE", "User", updated.getId(), null);

            return UserMapper.toDTO(updated);
        }
        return null;
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            auditLogService.log("DELETE", "User", id, null);
            return true;
        }
        return false;
    }
}

