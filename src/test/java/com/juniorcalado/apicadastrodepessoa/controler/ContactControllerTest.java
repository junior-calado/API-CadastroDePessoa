package com.juniorcalado.apicadastrodepessoa.controler;

import com.juniorcalado.apicadastrodepessoa.controller.ContactController;
import com.juniorcalado.apicadastrodepessoa.domain.People;
import com.juniorcalado.apicadastrodepessoa.repository.PeopleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleRepository peopleRepository;

    @Autowired
    private ContactController contactController;

    @Test
    public void testCreatePeopleWithValidationError() {
        People people = new People();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<Object> result = contactController.createPeople(people, bindingResult);

        assertNotNull(result);
        assertEquals(400, result.getStatusCodeValue());
    }

    @Test
    public void testCreatePeopleWithDuplicateCPF() {
        People people = new People();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(peopleRepository.hasCpf(people.getCpf())).thenReturn(true);

        ResponseEntity<Object> result = contactController.createPeople(people, bindingResult);

        assertNotNull(result);
        assertEquals(400, result.getStatusCodeValue());
    }

    @Test
    public void testGetPeopleByIdNotFound() {
        Long id = 1L;
        when(peopleRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<People> result = contactController.findById(id);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
    }

    @Test
    public void testDeletePeopleNotFound() {
        Long id = 1L;
        when(peopleRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<People> result = contactController.deletePeople(id);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
    }

    @Test
    public void testGetPeopleById() {
        Long id = 1L;
        People people = new People();
        when(peopleRepository.findById(id)).thenReturn(Optional.of(people));

        ResponseEntity<People> result = contactController.findById(id);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(people, result.getBody());
    }

    @Test
    public void testCreatePeopleWithMissingData() {
        People people = new People();

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<Object> result = contactController.createPeople(people, bindingResult);

        assertNotNull(result);
        assertEquals(400, result.getStatusCodeValue());
    }

    @Test
    public void testFindPeopleByIdFound() {
        Long id = 1L;
        People people = new People();
        people.setId(id);

        when(peopleRepository.findById(id)).thenReturn(Optional.of(people));

        ResponseEntity<People> result = contactController.findById(id);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(people, result.getBody());
    }

    @Test
    public void testFindPeopleByIdNotFound() {
        Long id = 1L;

        when(peopleRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<People> result = contactController.findById(id);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
    }
}
