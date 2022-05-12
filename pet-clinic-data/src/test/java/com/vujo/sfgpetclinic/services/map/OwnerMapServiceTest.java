package com.vujo.sfgpetclinic.services.map;

import com.vujo.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    Long id = 1L;
    String lastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(id).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();

        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(id);

        assertNotNull(owner);
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(id);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(id));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void saveWithId() {
        ownerMapService.save(Owner.builder().id(2L).build());

        assertEquals(2, ownerMapService.findAll().size());
    }

    @Test
    void saveWithoutId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        assertEquals(2, ownerMapService.findAll().size());
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName(lastName);

        assertNotNull(smith);
        assertEquals(lastName, smith.getLastName());
    }
}