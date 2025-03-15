package com.library.controllers;

import com.library.entities.Patron;
import com.library.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    // Get all patrons
    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    // Get a patron by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.getPatronById(id);
        return ResponseEntity.ok(patron);
    }

    // Add a new patron
    @PostMapping
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        Patron newPatron = patronService.addPatron(patron);
        return ResponseEntity.ok(newPatron);
    }

    // Update an existing patron
    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patronDetails) {
        Patron updatedPatron = patronService.updatePatron(id, patronDetails);
        return ResponseEntity.ok(updatedPatron);
    }

    // Delete a patron
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}