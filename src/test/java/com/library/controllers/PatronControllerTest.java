package com.library.controllers;

import com.library.entities.Patron;
import com.library.services.PatronService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Test
    void getAllPatrons() throws Exception {
        // Arrange
        Patron patron1 = new Patron();
        patron1.setName("Patron 1");
        Patron patron2 = new Patron();
        patron2.setName("Patron 2");
        List<Patron> patrons = Arrays.asList(patron1, patron2);

        when(patronService.getAllPatrons()).thenReturn(patrons);

        // Act & Assert
        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Patron 1"))
                .andExpect(jsonPath("$[1].name").value("Patron 2"));
    }

    @Test
    void getPatronById() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Patron 1");

        when(patronService.getPatronById(1L)).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Patron 1"));
    }

    @Test
    void addPatron() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setName("New Patron");

        when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Patron\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Patron"));
    }

    @Test
    void updatePatron() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Updated Patron");

        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Patron\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Patron"));
    }

    @Test
    void deletePatron() throws Exception {
        // Arrange
        doNothing().when(patronService).deletePatron(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }
}