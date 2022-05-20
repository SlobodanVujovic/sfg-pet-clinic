package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.model.Pet;
import com.vujo.sfgpetclinic.model.PetType;
import com.vujo.sfgpetclinic.services.OwnerService;
import com.vujo.sfgpetclinic.services.PetService;
import com.vujo.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final PetService petService;

    public PetController(OwnerService ownerService, PetTypeService petTypeService, PetService petService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @ModelAttribute("owner")
    public Owner setOwner(@PathVariable Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        return owner;
    }

    @ModelAttribute("types")
    public Set<PetType> setTypes() {
        Set<PetType> petTypes = petTypeService.findAll();
        return petTypes;
    }

    @GetMapping("/pets/new")
    public String getPets(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.addPet(pet);
        model.addAttribute("pet", pet);

        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    public String createPet(Owner owner, Pet pet) {
        owner.addPet(pet);
        petService.save(pet);

        return "redirect:/owners/" + owner.getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String editPets(Model model, @PathVariable Long petId) {
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);

        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/{petId}/edit")
    public String saveEditedPets(@PathVariable Long petId, Pet pet, Owner owner) {
        Pet existingPet = petService.findById(petId);

        existingPet.setPetType(pet.getPetType());
        existingPet.setName(pet.getName());
        existingPet.setBirthDate(pet.getBirthDate());

        petService.save(existingPet);

        return "redirect:/owners/" + owner.getId();
    }

}
