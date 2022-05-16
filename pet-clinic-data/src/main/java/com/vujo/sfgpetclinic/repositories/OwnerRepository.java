package com.vujo.sfgpetclinic.repositories;

import com.vujo.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    public Owner findByLastName(String lastName);

    public List<Owner> findAllByLastNameLikeIgnoreCase(String lastName);
}
