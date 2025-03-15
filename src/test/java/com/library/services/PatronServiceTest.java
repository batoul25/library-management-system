package com.library.services;

import com.library.entities.Patron;
import com.library.repositories.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatrons() {
        // Arrange
        Patron patron1 = new Patron();
        patron1.setName("Patron 1");
        Patron patron2 = new Patron();
        patron2.setName("Patron 2");
        List<Patron> patrons = Arrays.asList(patron1, patron2);

        when(patronRepository.findAll()).thenReturn(patrons);

        // Act
        List<Patron> result = patronService.getAllPatrons();

        // Assert
        assertEquals(2, result.size());
        verify(patronRepository, times(1)).findAll();
    }

    @Test
    void getPatronById() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Patron 1");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        // Act
        Patron result = patronService.getPatronById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Patron 1", result.getName());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void addPatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setName("New Patron");

        when(patronRepository.save(patron)).thenReturn(patron);

        // Act
        Patron result = patronService.addPatron(patron);

        // Assert
        assertNotNull(result);
        assertEquals("New Patron", result.getName());
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    void updatePatron() {
        // Arrange
        Patron existingPatron = new Patron();
        existingPatron.setId(1L);
        existingPatron.setName("Old Name");

        Patron updatedPatron = new Patron();
        updatedPatron.setName("New Name");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.save(existingPatron)).thenReturn(existingPatron);

        // Act
        Patron result = patronService.updatePatron(1L, updatedPatron);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).save(existingPatron);
    }

    @Test
    void deletePatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        doNothing().when(patronRepository).deleteById(1L);

        // Act
        patronService.deletePatron(1L);

        // Assert
        verify(patronRepository, times(1)).deleteById(1L);
    }
}