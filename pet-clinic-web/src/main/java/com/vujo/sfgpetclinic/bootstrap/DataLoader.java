package com.vujo.sfgpetclinic.bootstrap;

import com.vujo.sfgpetclinic.model.*;
import com.vujo.sfgpetclinic.services.OwnerService;
import com.vujo.sfgpetclinic.services.PetTypeService;
import com.vujo.sfgpetclinic.services.SpecialityService;
import com.vujo.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        int petTypeNumber = petTypeService.findAll().size();

        if (petTypeNumber == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");

        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Labrador");

        PetType savedCatPetType = petTypeService.save(cat);

        System.out.println("Loaded pet types...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("Beogradska 1");
        owner1.setCity("Beograd");
        owner1.setTelephone("064/11 22 333");
        Pet pet1 = new Pet();
        pet1.setName("Meda");
        pet1.setPetType(savedDogPetType);
        pet1.setBirthDate(LocalDate.of(2020, 1, 1));
        pet1.setOwner(owner1);
        owner1.getPets().add(pet1);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("Beogradska 2");
        owner2.setCity("Beograd");
        owner2.setTelephone("064/11 22 444");
        Pet pet2 = new Pet();
        pet2.setName("Kitty");
        pet2.setPetType(savedCatPetType);
        pet2.setBirthDate(LocalDate.of(2020, 2, 2));
        pet2.setOwner(owner2);
        owner2.getPets().add(pet2);

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Speciality speciality1 = new Speciality();
        speciality1.setDescription("radiology");

        Speciality savedSpeciality1 = specialityService.save(speciality1);

        Speciality speciality2 = new Speciality();
        speciality2.setDescription("surgery");

        Speciality savedSpeciality2 = specialityService.save(speciality2);

        System.out.println("Loaded specialities...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedSpeciality1);
        vet1.getSpecialities().add(savedSpeciality2);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessi");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSpeciality2);

        vetService.save(vet2);

        System.out.println("Loaded vets ...");
    }
}
