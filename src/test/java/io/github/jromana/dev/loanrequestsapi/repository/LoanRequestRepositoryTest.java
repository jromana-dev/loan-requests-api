package io.github.jromana.dev.loanrequestsapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.jromana.dev.loanrequestsapi.domain.LoanRequest;
import io.github.jromana.dev.loanrequestsapi.domain.LoanStatus;

class LoanRequestRepositoryTest {

    private LoanRequestRepository repository;

    /**
     * Inicializa un nuevo repositorio in-memory antes de cada test
     * para asegurar que los tests sean independientes y consistentes.
     */
    @BeforeEach
    void setup() {
        repository = new LoanRequestRepository();
    }

    /**
     * Verifica que un LoanRequest se puede guardar correctamente
     * y posteriormente recuperar por su ID.
     * Comprueba que el ID generado no es nulo y que los datos se mantienen.
     */
    @Test
    void testSaveAndFindById() {
        LoanRequest loan = new LoanRequest();
        loan.setApplicantName("Pedro");
        loan.setAmount(500);
        loan.setCurrency("EUR");
        loan.setDocumentId("99999999X");
        loan.setCreationDate(LocalDate.now());
        loan.setStatus(LoanStatus.PENDIENTE);

        LoanRequest saved = repository.save(loan);
        assertNotNull(saved.getId());

        Optional<LoanRequest> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Pedro", found.get().getApplicantName());
    }

    /**
     * Verifica que findAll() devuelve correctamente todos los préstamos
     * almacenados en el repositorio in-memory.
     * Se insertan dos préstamos y se espera que la lista resultante tenga tamaño 2.
     */
    @Test
    void testFindAll() {
        LoanRequest loan1 = new LoanRequest();
        loan1.setApplicantName("Ana");
        loan1.setAmount(300);
        loan1.setCurrency("EUR");
        loan1.setDocumentId("88888888Y");
        loan1.setCreationDate(LocalDate.now());
        loan1.setStatus(LoanStatus.PENDIENTE);
        repository.save(loan1);

        LoanRequest loan2 = new LoanRequest();
        loan2.setApplicantName("Luis");
        loan2.setAmount(700);
        loan2.setCurrency("EUR");
        loan2.setDocumentId("77777777Z");
        loan2.setCreationDate(LocalDate.now());
        loan2.setStatus(LoanStatus.PENDIENTE);
        repository.save(loan2);

        List<LoanRequest> all = repository.findAll();
        assertEquals(2, all.size());
    }

    /**
     * Verifica que un LoanRequest existente se puede actualizar correctamente.
     * Se cambia el estado del préstamo a APROBADA y se comprueba que el cambio se refleje.
     */
    @Test
    void testUpdate() {
        LoanRequest loan = new LoanRequest();
        loan.setApplicantName("Maria");
        loan.setAmount(400);
        loan.setCurrency("EUR");
        loan.setDocumentId("66666666A");
        loan.setCreationDate(LocalDate.now());
        loan.setStatus(LoanStatus.PENDIENTE);

        LoanRequest saved = repository.save(loan);
        saved.setStatus(LoanStatus.APROBADA);

        LoanRequest updated = repository.update(saved);
        assertEquals(LoanStatus.APROBADA, updated.getStatus());
    }
}
