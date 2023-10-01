package com.juniorcalado.apicadastrodepessoa.controller;

import com.juniorcalado.apicadastrodepessoa.services.PaginatedPeople;
import com.juniorcalado.apicadastrodepessoa.domain.People;
import com.juniorcalado.apicadastrodepessoa.repository.PeopleRepository;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

    @Autowired//Injeta dependencia automatica
    private final PeopleRepository peopleRepository;

    public ContactController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @GetMapping
    public PaginatedPeople getPessoasPaginadas(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int internalPage = page - 1;
        List<People> allPessoas = peopleRepository.findAllPessoas();
        int totalElements = allPessoas.size();

        int startIndex = internalPage * size;
        int endIndex = Math.min(startIndex + size, totalElements);

        if (startIndex >= totalElements) {
            return new PaginatedPeople(Collections.emptyList(), page, 0, size, totalElements);
        }

        List<People> pessoasPaginadas = allPessoas.subList(startIndex, endIndex);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PaginatedPeople(pessoasPaginadas, page, totalPages, size, totalElements);
    }

    @GetMapping("/search")
    public ResponseEntity<List<People>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf
    ){
        List<People> pessoas = peopleRepository.findAllByParams(nome, cpf);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<People> findById(@PathVariable Long id){
        Optional<People> pessoa = peopleRepository.findById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa.get());
        } else {
            //Da pra criar uma exception
            return ResponseEntity.notFound().build();
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

        if (people.getContatos() == null || people.getContatos().isEmpty()) {
            return ResponseEntity.badRequest().body("É necessário fornecer pelo menos um contato.");
        }

        if (peopleRepository.hasCpf(people.getCpf())) {
            return ResponseEntity.badRequest().body("Este CPF já esta cadastrado.");
        }

        People savePeople = peopleRepository.save(people);
        return ResponseEntity.ok(savePeople);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePeople(@PathVariable Long id, @Valid @RequestBody People people) {
        if (people.getContatos() == null || people.getContatos().isEmpty()) {
            return ResponseEntity.badRequest().body("É necessário fornecer pelo menos um contato.");
        }

        Optional<People> hasPeople = peopleRepository.findById(id);
        if (hasPeople.isPresent()) {

            People updatePeople = hasPeople.get();

            updatePeople.setNome(people.getNome());
            updatePeople.setCpf(people.getCpf());
            updatePeople.setDataNascimento(people.getDataNascimento());

            updatePeople.getContatos().clear();

            updatePeople.getContatos().addAll(people.getContatos());

            peopleRepository.save(updatePeople);
            return ResponseEntity.ok(updatePeople);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<People> deletePeople(@PathVariable Long id){
        Optional<People> pessoa = peopleRepository.findById(id);
        if (pessoa.isPresent()) {
            peopleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            //Da pra criar uma exception
            return ResponseEntity.notFound().build();
        }
    }


}
