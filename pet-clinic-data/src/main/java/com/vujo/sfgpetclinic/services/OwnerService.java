package com.vujo.sfgpetclinic.services;

import com.vujo.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
