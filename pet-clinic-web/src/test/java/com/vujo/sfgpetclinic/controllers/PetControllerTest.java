package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.model.Pet;
import com.vujo.sfgpetclinic.services.OwnerService;
import com.vujo.sfgpetclinic.services.PetService;
import com.vujo.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    OwnerService ownerService;
    @Mock
    PetTypeService petTypeService;
    @Mock
    PetService petService;
    @InjectMocks
    PetController petController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).pets(new HashSet<>()).build());
        when(petTypeService.findAll()).thenReturn(new HashSet<>());
    }

    @Test
    public void testInitCreatePet() throws Exception {
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner", "types", "pet"))
                .andExpect(model().attribute("pet", hasProperty("owner", notNullValue())))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void testProcessCreatePet() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);

        when(petService.save(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());
    }

    @Test
    public void testInitUpdatePet() throws Exception {
        Pet pet = new Pet();
        pet.setName("Misko");

        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner", "types", "pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void testProcessEditPet() throws Exception {
        Pet pet = new Pet();
        pet.setName("Misko");
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }
}