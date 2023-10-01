import com.juniorcalado.apicadastrodepessoa.controller.ContactController;
import com.juniorcalado.apicadastrodepessoa.domain.People;
import com.juniorcalado.apicadastrodepessoa.repository.PeopleRepository;
import com.juniorcalado.apicadastrodepessoa.services.PaginatedPeople;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerTest {

    private ContactController contactController;

    @InjectMocks
    private ContactController pessoaService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private PeopleRepository peopleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        contactController = new ContactController(peopleRepository);
    }


    @Test
    public void testCreatePessoa() {
        People pessoa = new People();
        pessoa.setNome("Maria");

        when(peopleRepository.save(any(People.class))).thenReturn(pessoa);

        People result = pessoaService.createPeople(pessoa);

        assertNotNull(result);
        assertEquals("Maria", result.getNome());
    }


    @Test
    public void testUpdatePeople() {
        // Simulando dados para o teste
        Long id = 1L;
        People existingPeople = new People(/* preencher com dados de exemplo */);
        People updatedPeople = new People(/* preencher com dados de exemplo atualizados */);

        when(peopleRepository.findById(id)).thenReturn(Optional.of(existingPeople));

        // Chamar o método e verificar a resposta
        ResponseEntity<Object> result = contactController.updatePeople(id, updatedPeople);
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue()); // Verificar o status code esperado
    }

    @Test
    public void testDeletePessoa() {
        // Simular uma pessoa existente com ID 1L no banco de dados (seu caso pode variar)
        Long id = 1L;

        // Montar a URL do endpoint
        String url = "http://localhost:" + port + "/pessoas/" + id;

        try {
            // Enviar uma solicitação DELETE para o endpoint da API
            restTemplate.delete(url);

            // Se a exclusão for bem-sucedida, o endpoint deve retornar 404 (NOT_FOUND)
            HttpStatus responseStatusCode = restTemplate.getForEntity(url, Void.class).getStatusCode();
            assertEquals(HttpStatus.NOT_FOUND, responseStatusCode); // Status esperado após a exclusão
        } catch (Exception e) {
            // O try-catch captura a exceção quando a pessoa não é encontrada (404)
            assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(e.getMessage()));
        }
    }
}
