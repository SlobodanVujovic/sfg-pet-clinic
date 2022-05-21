package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.formatters.LocalDateFormatter;
import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.model.Pet;
import com.vujo.sfgpetclinic.services.OwnerService;
import com.vujo.sfgpetclinic.services.PetService;
import com.vujo.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {

    @Mock
    OwnerService ownerService;
    @Mock
    PetService petService;
    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        DefaultFormattingConversionService defaultFormatter = new DefaultFormattingConversionService();
        LocalDateFormatter myCustomFormatter = new LocalDateFormatter();
        defaultFormatter.addFormatterForFieldType(LocalDate.class, myCustomFormatter);

        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .setConversionService(defaultFormatter)
                .build();
    }

    @Test
    public void testInitNewVisit() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        Pet pet = Pet.builder().build();
        pet.setVisits(new HashSet<>());
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("owner", "pet", "visit"));

        verifyNoInteractions(visitService);
    }

    @Test
    public void testSaveNewVisit() throws Exception {
        Pet pet = new Pet();
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("date", "2020-01-01")
                        .param("description", "someDescription")
                        .param("petId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }
}
