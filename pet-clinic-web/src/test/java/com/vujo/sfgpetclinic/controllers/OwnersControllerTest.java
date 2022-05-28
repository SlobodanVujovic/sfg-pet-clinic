package com.vujo.sfgpetclinic.controllers;

import com.vujo.sfgpetclinic.exceptions.ControllerExceptionHandler;
import com.vujo.sfgpetclinic.model.Owner;
import com.vujo.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnersControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnersController ownersController;

    MockMvc mockMvc;
    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownersController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testOwnerDetails() throws Exception {
        Owner owner = Owner.builder().id(1L).build();
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", notNullValue()));
    }

    @Test
    public void testFindOwner() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void testFindFormReturnMany() throws Exception {
        List<Owner> owners = new ArrayList<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attributeExists("owners"));

        verify(ownerService).findAllByLastNameLike(anyString());
    }

    @Test
    public void testFindFormReturnOne() throws Exception {
        List<Owner> owners = new ArrayList<>();
        owners.add(Owner.builder().id(2L).build());
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/2"));

        verify(ownerService).findAllByLastNameLike(anyString());
    }

    @Test
    public void testFindFormReturnNone() throws Exception {
        List<Owner> owners = new ArrayList<>();
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));

        verify(ownerService).findAllByLastNameLike(anyString());
    }

    @Test
    public void testInitCreateOwner() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attribute("owner", notNullValue()));

        verifyNoInteractions(ownerService);
    }

    @Test
    public void testProcessCreateOwner() throws Exception {
        when(ownerService.save(any(Owner.class))).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/owners/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "miki")
                        .param("lastName", "riki")
                        .param("address", "Beogradska")
                        .param("city", "BG")
                        .param("telephone", "123")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).save(any(Owner.class));
    }

    @Test
    public void testProcessCreateOwnerValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "miki")
                        .param("lastName", "riki")
                        .param("city", "BG")
                        .param("telephone", "123")
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void testInitUpdateOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService).findById(anyLong());
    }

    @Test
    public void testProcessUpdateOwner() throws Exception {
        when(ownerService.save(any(Owner.class))).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    public void testNotFoundOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("notFound"));
    }

    @Test
    public void testNumberFormatException() throws Exception {
        mockMvc.perform(get("/owners/shouldBeLong"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("badRequest"));
    }
}