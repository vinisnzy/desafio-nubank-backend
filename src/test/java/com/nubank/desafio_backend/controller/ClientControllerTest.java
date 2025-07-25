package com.nubank.desafio_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubank.desafio_backend.dto.client.ClientRequest;
import com.nubank.desafio_backend.dto.client.ClientResponse;
import com.nubank.desafio_backend.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;

    private ClientRequest request;
    private ClientResponse response;
    private Long id;

    @BeforeEach
    void setUp() {
        id = 1L;
        request = new ClientRequest(
                "Nome do cliente",
                "cliente@gmail.com"
        );
        response = new ClientResponse(
                id,
                request.name(),
                request.email(),
                List.of()
        );
    }

    @Test
    void shouldCreateClient() throws Exception {
        when(clientService.createClient(any(ClientRequest.class))).thenReturn(response);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void shouldThrowsBadRequestWhenCreatingClientWithInvalidData() throws Exception {
        request = new ClientRequest("", "invalid-email");

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllClients() throws Exception {
        when(clientService.getAllClients()).thenReturn(List.of(response));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(request.name()))
                .andExpect(jsonPath("$[0].email").value(request.email()));
    }

    @Test
    void shouldGetContactsByClientId() throws Exception {
        when(clientService.getContactsByClientId(id)).thenReturn(List.of());

        mockMvc.perform(get("/clientes/{id}/contatos", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldDeleteClient() throws Exception {
        clientService.deleteClient(id);

        mockMvc.perform(delete("/clientes/{id}", id))
                .andExpect(status().isNoContent());
    }
}