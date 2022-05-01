package com.vujo.sfgpetclinic.services;

import com.vujo.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService {
    Owner findById(Long id);

    Set<Owner> findAll();

    Owner save(Owner owner);

    Owner findByLastName(String lastName);
}
