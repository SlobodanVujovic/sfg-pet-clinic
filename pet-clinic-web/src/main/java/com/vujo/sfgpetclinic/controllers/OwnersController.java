package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnersController {

    private final OwnerService ownerService;

    public OwnersController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{ownerId}")
    public ModelAndView getOwner(@PathVariable String ownerId) {
        Owner owner = ownerService.findById(Long.valueOf(ownerId));

        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner", owner);

        return modelAndView;
    }

    @GetMapping("/find")
    public String findOwner(String someProp, Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping
    public String getOwners(Owner owner, Model model, BindingResult result) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        String lastName = owner.getLastName();
        List<Owner> owners = ownerService.findAllByLastNameLike("%" + lastName + "%");
        if (owners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (owners.size() == 1) {
            owner = owners.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("owners", owners);
            return "owners/ownersList";
        }
    }

    @GetMapping("/new")
    public String initCreateOwner(Model model) {
        model.addAttribute("owner", Owner.builder().build());

        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String createOwner(Owner owner, Model model) {
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{id}/edit")
    public String initUpdateOwner(@PathVariable Long id, Model model) {
        Owner owner = ownerService.findById(id);
        model.addAttribute("owner", owner);

        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{id}/edit")
    public String processUpdateOwner(Owner owner, @PathVariable Long id) {
        owner.setId(id);
        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }
}
