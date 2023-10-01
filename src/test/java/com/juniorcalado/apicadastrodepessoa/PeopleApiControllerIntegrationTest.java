package com.juniorcalado.apicadastrodepessoa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juniorcalado.apicadastrodepessoa.domain.People;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PeopleApiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Limpar ou preparar o estado do banco de dados, se necessário, antes dos testes
    }

    @Test
    public void testCreatePeople() throws Exception {
        People people = new People();
        people.setNome("Junior");
        people.setCpf("10426812956");
        // Defina outros campos e contatos conforme necessário

        ResultActions resultActions = mockMvc.perform(post("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(people)));

        resultActions
                .andExpect(status().isCreated()) // Verificar se o status é 201 (CREATED)
                .andExpect(jsonPath("$.id").exists()); // Verificar se um ID foi gerado
    }

    @Test
    public void testGetPeopleById() throws Exception {
        // Crie uma pessoa no banco de dados ou prepare um cenário de teste com uma pessoa existente
        Long id = 1L; // Suponha que esta seja a ID da pessoa criada ou existente

        ResultActions resultActions = mockMvc.perform(get("/api/contacts/{id}", id));

        resultActions
                .andExpect(status().isOk()) // Verificar se o status é 200 (OK)
                .andExpect(jsonPath("$.id").value(id)); // Verificar se o ID retornado corresponde ao ID esperado
    }

    @Test
    public void testUpdatePeople() throws Exception {
        // Crie uma pessoa no banco de dados ou prepare um cenário de teste com uma pessoa existente
        Long id = 1L; // Suponha que esta seja a ID da pessoa criada ou existente

        People updatedPeople = new People();
        updatedPeople.setNome("João Silva"); // Atualize o nome

        ResultActions resultActions = mockMvc.perform(put("/api/contacts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPeople)));

        resultActions
                .andExpect(status().isOk()) // Verificar se o status é 200 (OK)
                .andExpect(jsonPath("$.nome").value("João Silva")); // Verificar se o nome foi atualizado corretamente
    }

    @Test
    public void testDeletePeople() throws Exception {
        // Crie uma pessoa no banco de dados ou prepare um cenário de teste com uma pessoa existente
        Long id = 1L; // Suponha que esta seja a ID da pessoa criada ou existente

        ResultActions resultActions = mockMvc.perform(delete("/api/contacts/{id}", id));

        resultActions
                .andExpect(status().isNoContent()); // Verificar se o status é 204 (NO CONTENT)
    }
}
