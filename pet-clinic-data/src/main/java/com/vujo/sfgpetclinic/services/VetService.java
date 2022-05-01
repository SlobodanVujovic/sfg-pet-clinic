package com.vujo.sfgpetclinic.services;

import com.vujo.sfgpetclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet findById(Long id);

    Set<Vet> findAll();

    Vet save(Vet owner);
}
