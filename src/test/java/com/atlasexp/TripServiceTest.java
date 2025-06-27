package com.atlasexp.service;

import com.atlasexp.dto.TripDTO;
import com.atlasexp.mapper.TripMapper;
import com.atlasexp.model.Trip;
import com.atlasexp.model.User;
import com.atlasexp.repository.TripRepository;
import com.atlasexp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TripServiceTest {

    @InjectMocks
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditLogService auditLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrip() {
        // Arrange
        TripDTO dto = new TripDTO();
        dto.setTitle("Viagem Teste");
        dto.setDestination("São Paulo");
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(3));
        dto.setUserId(1L);

        User mockUser = new User();
        mockUser.setId(1L);

        Trip savedTrip = new Trip();
        savedTrip.setId(10L);
        savedTrip.setTitle(dto.getTitle());
        savedTrip.setDestination(dto.getDestination());

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(tripRepository.save(any(Trip.class))).thenReturn(savedTrip);

        // Act
        TripDTO result = tripService.createTrip(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Viagem Teste", result.getTitle());
        verify(auditLogService).log("CREATE", "Trip", savedTrip.getId(), null);
    }
}
