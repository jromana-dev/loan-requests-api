package io.github.jromana.dev.loanrequestsapi.service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.jromana.dev.loanrequestsapi.domain.LoanRequest;
import io.github.jromana.dev.loanrequestsapi.domain.LoanStatus;
import io.github.jromana.dev.loanrequestsapi.exception.InvalidLoanStatusTransitionException;
import io.github.jromana.dev.loanrequestsapi.repository.LoanRequestRepository;

class LoanRequestServiceImplTest {

    private LoanRequestServiceImpl service;

    /**
     * Inicializa el servicio con un repositorio in-memory antes de cada test.
     * Esto asegura que cada prueba sea independiente y tenga un estado limpio.
     */
    @BeforeEach
    void setup() {
        service = new LoanRequestServiceImpl(new LoanRequestRepository());
    }

    /**
     * Verifica que al crear un nuevo préstamo con createLoanRequest:
     * - El estado inicial se establece correctamente como PENDIENTE.
     * - Se genera un ID único para el préstamo.
     */
    @Test
    void testCreateLoanRequestSetsStatusPendiente() {
        LoanRequest loan = new LoanRequest();
        loan.setApplicantName("Javier");
        loan.setAmount(1000);
        loan.setCurrency("EUR");
        loan.setDocumentId("12345678A");
        loan.setCreationDate(LocalDate.now());

        LoanRequest created = service.createLoanRequest(loan);

        assertEquals(LoanStatus.PENDIENTE, created.getStatus());
        assertNotNull(created.getId());
    }

    /**
     * Verifica que las transiciones de estado válidas funcionan correctamente:
     * - PENDIENTE -> APROBADA
     * - APROBADA -> CANCELADA
     * Comprueba que el estado del préstamo se actualiza según la lógica del servicio.
     */
    @Test
    void testUpdateStatusValidTransition() {
        LoanRequest loan = new LoanRequest();
        loan.setApplicantName("Ana");
        loan.setAmount(500);
        loan.setCurrency("EUR");
        loan.setDocumentId("87654321B");
        loan.setCreationDate(LocalDate.now());

        LoanRequest created = service.createLoanRequest(loan);

        // PENDIENTE -> APROBADA
        LoanRequest updated = service.updateLoanRequestStatus(created.getId(), LoanStatus.APROBADA);
        assertEquals(LoanStatus.APROBADA, updated.getStatus());

        // APROBADA -> CANCELADA
        updated = service.updateLoanRequestStatus(created.getId(), LoanStatus.CANCELADA);
        assertEquals(LoanStatus.CANCELADA, updated.getStatus());
    }

    /**
     * Verifica que una transición de estado inválida lanza la excepción esperada:
     * - PENDIENTE -> CANCELADA no está permitida según la lógica de negocio.
     * Comprueba que se lanza InvalidLoanStatusTransitionException.
     */
    @Test
    void testUpdateStatusInvalidTransitionThrowsException() {
        LoanRequest loan = new LoanRequest();
        loan.setApplicantName("Nuria");
        loan.setAmount(200);
        loan.setCurrency("EUR");
        loan.setDocumentId("11223344C");
        loan.setCreationDate(LocalDate.now());

        LoanRequest created = service.createLoanRequest(loan);

        long id = created.getId();

        // PENDIENTE -> CANCELADA es inválido
        Throwable exception = assertThrows(InvalidLoanStatusTransitionException.class, () -> {
            service.updateLoanRequestStatus(id, LoanStatus.CANCELADA);
        });
        assertNotNull(exception);
    }
}

