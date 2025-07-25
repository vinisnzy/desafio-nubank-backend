package com.nubank.desafio_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubank.desafio_backend.dto.contact.ContactRequest;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContactService service;

    private ContactRequest request;
    private ContactResponse response;
    private Long id;

    @BeforeEach
    void setUp() {
        id = 1L;
        request = new ContactRequest(
                "nome do contato",
                "contato@gmail.com",
                id
        );
        response = new ContactResponse(
                id,
                request.name(),
                request.email()
        );
    }

    @Test
    void shouldCreateContact() throws Exception {
        when(service.createContact(any(ContactRequest.class))).thenReturn(response);

        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.email").value(response.email()));
    }

    @Test
    void shouldReturnBadRequestWhenDataIsInvalid() throws Exception {
        ContactRequest invalidRequest = new ContactRequest(
                "",
                "invalid-email",
                id
        );

        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}