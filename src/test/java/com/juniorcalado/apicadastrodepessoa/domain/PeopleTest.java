package com.juniorcalado.apicadastrodepessoa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PeopleTest {

    private People pessoa;

    @BeforeEach
    public void setUp() {
        // Configurar um objeto Pessoa antes de cada teste
        pessoa = new People();
    }

    @Test
    public void testSetNome() {
        // Definir o nome da pessoa
        pessoa.setNome("Junior");

        // Verificar se o nome foi definido corretamente
        assertEquals("Junior", pessoa.getNome());
    }

    @Test
    public void testSetCpf() {
        // Definir o CPF da pessoa
        pessoa.setCpf("104.268.169-56");

        // Verificar se o CPF foi definido corretamente
        assertEquals("104.268.169-56", pessoa.getCpf());
    }

    @Test
    public void testSetDataNascimento() {
        // Definir a data de nascimento da pessoa
        LocalDate dataNascimento = LocalDate.of(1990, 5, 15);
        pessoa.setDataNascimento(dataNascimento);

        // Verificar se a data de nascimento foi definida corretamente
        assertEquals(dataNascimento, pessoa.getDataNascimento());
    }


}
