package com.juniorcalado.apicadastrodepessoa;

import com.juniorcalado.apicadastrodepessoa.controller.ContactController;
import com.juniorcalado.apicadastrodepessoa.domain.People;
import com.juniorcalado.apicadastrodepessoa.repository.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiCadastroDePessoaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleRepository peopleRepository;

    @MockBean
    private People people;

    @Autowired
    private ContactController contactController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePeopleWithNoContacts() {
        People people = new People();
        people.setName("Ana da Silva");
        people.setCpf("12345678909");
        people.setBirthDate(LocalDate.parse("1990-01-01"));

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<Object> result = contactController.createPeople(people, bindingResult);

        assertNotNull(result);
        assertEquals(400, result.getStatusCodeValue());
    }

    @Test
    public void testGetPeopleById() {
        Long id = 1L;
        People existingPeople = new People();
        existingPeople.setId(id);
        existingPeople.setName("João da Silva");
        existingPeople.setCpf("12345678909");
        existingPeople.setBirthDate(LocalDate.parse("1990-01-01"));

        when(peopleRepository.findById(id)).thenReturn(Optional.of(existingPeople));

        ResponseEntity<People> result = contactController.findById(id);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(existingPeople, result.getBody());
    }

    @Test
    public void testGetPeopleByIdNotFound() {
        Long id = 1L;
        when(peopleRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<People> result = contactController.findById(id);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
    }
}

