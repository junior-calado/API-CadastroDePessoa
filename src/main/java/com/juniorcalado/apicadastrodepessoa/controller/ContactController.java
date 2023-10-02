package com.juniorcalado.apicadastrodepessoa.controller;

import com.juniorcalado.apicadastrodepessoa.exceptions.PeopleExceptions;
import com.juniorcalado.apicadastrodepessoa.services.PaginatedPeople;
import com.juniorcalado.apicadastrodepessoa.domain.People;
import com.juniorcalado.apicadastrodepessoa.repository.PeopleRepository;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contacts")
public class ContactController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private final PeopleRepository peopleRepository;

    public ContactController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @GetMapping
    public PaginatedPeople getPeoplePages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int internalPage = page - 1;
        List<People> allPeople = peopleRepository.findAllPeople();
        int totalElements = allPeople.size();

        int startIndex = internalPage * size;
        int endIndex = Math.min(startIndex + size, totalElements);

        if (startIndex >= totalElements) {
            return new PaginatedPeople(Collections.emptyList(), page, 0, size, totalElements);
        }

        List<People> peoplePaginated = allPeople.subList(startIndex, endIndex);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PaginatedPeople(peoplePaginated, page, totalPages, size, totalElements);
    }

    @GetMapping("/search")
    public ResponseEntity<List<People>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf
    ){
        List<People> people = peopleRepository.findAllByParams(nome, cpf);
        return ResponseEntity.ok(people);
    }

    @GetMapping("/{id}")
    public ResponseEntity<People> findById(@PathVariable Long id){
        Optional<People> people = peopleRepository.findById(id);
        if (people.isPresent()) {
            return ResponseEntity.ok(people.get());
        } else {
            throw new PeopleExceptions("ID not found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPeople(@Valid @RequestBody People people, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> erros = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                erros.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(erros);
        }

        if (people.getContacts() == null || people.getContacts().isEmpty()) {
            return ResponseEntity.badRequest().body("You must provide at least one contact");
        }

        if (peopleRepository.hasCpf(people.getCpf())) {
            return ResponseEntity.badRequest().body("This CPF is already registered");
        }

        People savePeople = peopleRepository.save(people);
        return ResponseEntity.ok(savePeople);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePeople(@PathVariable Long id, @Valid @RequestBody People people) {
        if (people.getContacts() == null || people.getContacts().isEmpty()) {
            return ResponseEntity.badRequest().body("You must provide at least one contact");
        }

        Optional<People> hasPeople = peopleRepository.findById(id);
        if (hasPeople.isPresent()) {

            People updatePeople = hasPeople.get();

            updatePeople.setName(people.getName());
            updatePeople.setCpf(people.getCpf());
            updatePeople.setBirthDate(people.getBirthDate());

            updatePeople.getContacts().clear();

            updatePeople.getContacts().addAll(people.getContacts());

            peopleRepository.save(updatePeople);
            return ResponseEntity.ok(updatePeople);
        } else {
            throw new PeopleExceptions("Unable to update as the ID provided is invalid");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<People> deletePeople(@PathVariable Long id){
        Optional<People> people = peopleRepository.findById(id);
        if (people.isPresent()) {
            peopleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new PeopleExceptions("Unable to delete as the ID provided is invalid");
        }
    }


}
