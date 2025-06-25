package com.atlasexp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ActivityDTO {

    private Long id;

    @NotBlank(message = "O nome da atividade é obrigatório")
    private String name;

    @NotBlank(message = "A descrição da atividade é obrigatória")
    private String description;

    @NotNull(message = "A data/hora da atividade é obrigatória")
    @Future(message = "A data/hora da atividade deve ser futura")
    private LocalDateTime dateTime;

    @NotNull(message = "O ID da viagem é obrigatório")
    private Long tripId;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }
}
