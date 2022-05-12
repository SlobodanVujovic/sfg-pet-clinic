package com.vujo.sfgpetclinic.services.springdatajpa;

import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;
    @InjectMocks
    OwnerSDJpaService service;

    Owner owner;
    Long id = 1L;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(id).lastName(LAST_NAME).build();

    }

    @Test
    void findAll() {
        List<Owner> owners = new ArrayList<>();
        owners.add(owner);
        when(ownerRepository.findAll()).thenReturn(owners);

        assertEquals(1, service.findAll().size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        assertEquals(owner.getId(), service.findById(id).getId());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());

        assertNull(service.findById(id));
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner);

        assertEquals(owner, service.save(owner));
    }

    @Test
    void delete() {
        service.delete(owner);

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(id);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(owner);

        assertEquals(LAST_NAME, service.findByLastName(LAST_NAME).getLastName());
    }
}