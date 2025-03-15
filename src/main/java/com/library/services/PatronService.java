// package com.library.services;

// import com.library.entities.Patron;
// import com.library.repositories.PatronRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cache.annotation.CacheEvict;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.cache.annotation.CachePut;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class PatronService {

//     @Autowired
//     private PatronRepository patronRepository;

//     // Cache the result of getAllPatrons()
//     @Cacheable("patrons")
//     @Transactional(readOnly = true) // Read-only transaction for fetching data
//     public List<Patron> getAllPatrons() {
//         return patronRepository.findAll();
//     }

//     // Cache the result of getPatronById() with the patron ID as the key
//     @Cacheable(value = "patron", key = "#id")
//     @Transactional(readOnly = true) // Read-only transaction for fetching data
//     public Patron getPatronById(Long id) {
//         Optional<Patron> patron = patronRepository.findById(id);
//         if (patron.isPresent()) {
//             return patron.get();
//         } else {
//             throw new RuntimeException("Patron not found with id: " + id);
//         }
//     }

//     // Update the cache when a new patron is added
//     @CachePut(value = "patron", key = "#patron.id")
//     @Transactional // Writable transaction for adding data
//     public Patron addPatron(Patron patron) {
//         return patronRepository.save(patron);
//     }

//     // Update the cache when a patron is updated
//     @CachePut(value = "patron", key = "#id")
//     @Transactional // Writable transaction for updating data
//     public Patron updatePatron(Long id, Patron patronDetails) {
//         Patron patron = getPatronById(id);
//         patron.setName(patronDetails.getName());
//         patron.setContactInfo(patronDetails.getContactInfo());
//         return patronRepository.save(patron);
//     }

//     // Evict the cache when a patron is deleted
//     @CacheEvict(value = "patron", key = "#id")
//     @Transactional // Writable transaction for deleting data
//     public void deletePatron(Long id) {
//         Patron patron = getPatronById(id);
//         patronRepository.delete(patron);
//     }
// }


package com.library.services;

import com.library.entities.Patron;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PatronService {

    @Transactional(readOnly = true)
    List<Patron> getAllPatrons();

    @Transactional(readOnly = true)
    Patron getPatronById(Long id);

    @Transactional
    Patron addPatron(Patron patron);

    @Transactional
    Patron updatePatron(Long id, Patron patronDetails);

    @Transactional
    void deletePatron(Long id);
}