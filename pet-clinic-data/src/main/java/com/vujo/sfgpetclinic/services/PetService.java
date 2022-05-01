package com.vujo.sfgpetclinic.services;


import com.vujo.sfgpetclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet findById(Long id);

    Set<Pet> findAll();

    Pet save(Pet owner);
}
