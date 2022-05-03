package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Vet;
import com.vujo.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping("/vets")
@Controller
public class VetsController {

    private final VetService vetService;

    public VetsController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        Set<Vet> vets = vetService.findAll();
        model.addAttribute("vets", vets);

        return "vets/index";
    }
}
