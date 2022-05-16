package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnersController {

    private final OwnerService ownerService;

    public OwnersController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        Set<Owner> owners = ownerService.findAll();
        model.addAttribute("owners", owners);

        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {

        return "owners/find";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView getOwner(@PathVariable String ownerId) {
        Owner owner = ownerService.findById(Long.valueOf(ownerId));

        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner", owner);

        return modelAndView;
    }
}
