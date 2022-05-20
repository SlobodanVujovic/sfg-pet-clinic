package com.vujo.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "pets")
public class Pet extends BaseEntity {
    private String name;

    @ManyToOne
    private PetType petType;

    @ManyToOne
    private Owner owner;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();
}
