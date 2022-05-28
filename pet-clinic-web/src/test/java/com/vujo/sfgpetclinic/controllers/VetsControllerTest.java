package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.model.Vet;
import com.vujo.sfgpetclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VetsControllerTest {
    @Mock
    VetService vetService;
    @InjectMocks
    VetsController vetController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(vetController)
                .build();
    }

    @Test
    public void testApiVetsRequest() throws Exception {
        Set<Vet> vets = new HashSet<>();
        vets.add(new Vet());
        vets.add(new Vet());
        when(vetService.findAll()).thenReturn(vets);

        MvcResult mvcResult = mockMvc.perform(get("/api/vets"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}