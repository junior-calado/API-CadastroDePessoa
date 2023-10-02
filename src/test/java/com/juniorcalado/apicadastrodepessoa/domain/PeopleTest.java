package com.juniorcalado.apicadastrodepessoa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PeopleTest {

    private People pessoa;

    @BeforeEach
    public void setUp() {
        pessoa = new People();
    }

    @Test
    public void testSetNome() {
        pessoa.setName("Junior");

        assertEquals("Junior", pessoa.getName());
    }

    @Test
    public void testSetCpf() {
        pessoa.setCpf("104.268.169-56");

        assertEquals("104.268.169-56", pessoa.getCpf());
    }

    @Test
    public void testSetDataNascimento() {
        LocalDate dataNascimento = LocalDate.of(1990, 5, 15);
        pessoa.setBirthDate(dataNascimento);

        assertEquals(dataNascimento, pessoa.getBirthDate());
    }
}
