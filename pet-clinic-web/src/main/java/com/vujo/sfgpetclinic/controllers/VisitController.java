package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.model.Pet;
import com.vujo.sfgpetclinic.model.Visit;
import com.vujo.sfgpetclinic.repositories.VisitRepository;
import com.vujo.sfgpetclinic.services.OwnerService;
import com.vujo.sfgpetclinic.services.PetService;
import com.vujo.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

    private final OwnerService ownerService;
    private final PetService petService;
    private final VisitService visitService;

    private final VisitRepository visitRepository;

    public VisitController(OwnerService ownerService, PetService petService, VisitService visitService, VisitRepository visitRepository) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.visitService = visitService;
        this.visitRepository = visitRepository;
    }

    @ModelAttribute
    public void setOwnerAndPet(@PathVariable Long ownerId, @PathVariable Long petId, Model model) {
        Owner owner = ownerService.findById(ownerId);
        Pet pet = petService.findById(petId);

        model.addAttribute("owner", owner);
        model.addAttribute("pet", pet);
    }

    @GetMapping("/new")
    public String initNewVisit(Pet pet, Model model) {
        Visit visit = new Visit();
        visit.setPet(pet);
        pet.getVisits().add(visit);
        model.addAttribute("visit", visit);

        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/new")
    public String saveNewVisit(Visit visit, Pet pet){
        pet.getVisits().add(visit);
        visit.setPet(pet);

        visitService.save(visit);

        return "redirect:/owners/1";
    }

}
