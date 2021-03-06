package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Vet;
import com.vujo.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetsController {

    private final VetService vetService;

    public VetsController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets/index.html", "/vets/index", "/vets.html"})
    public String index(Model model) {
        Set<Vet> vets = vetService.findAll();
        model.addAttribute("vets", vets);

        return "vets/index";
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getVets() {
        return vetService.findAll();
    }
}
