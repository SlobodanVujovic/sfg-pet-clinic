package com.vujo.sfgpetclinic.repositories;

import com.vujo.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
