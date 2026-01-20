package io.github.jromana.dev.loanrequestsapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.jromana.dev.loanrequestsapi.domain.LoanRequest;
import io.github.jromana.dev.loanrequestsapi.domain.LoanStatus;
import io.github.jromana.dev.loanrequestsapi.exception.InvalidLoanStatusTransitionException;
import io.github.jromana.dev.loanrequestsapi.repository.LoanRequestRepository;

/**
 * Implementación del servicio de solicitudes de préstamo en memoria.
 * Gestiona la creación, consulta y actualización de solicitudes.
 */
@Service
public class LoanRequestServiceImpl implements LoanRequestService {

    // Repositorio inyectado para persistencia en memoria
    private final LoanRequestRepository repository;

    public LoanRequestServiceImpl(LoanRequestRepository repository) {
        this.repository = repository;
    }

    /**
     * Crea una nueva solicitud de préstamo.
     * Todas las solicitudes empiezan con estado PENDIENTE.
     *
     * @param loanRequest la solicitud a crear
     * @return la solicitud creada con ID asignado
     */
    @Override
    public LoanRequest createLoanRequest(LoanRequest loanRequest) {
        loanRequest.setStatus(LoanStatus.PENDIENTE); // Todas empiezan como pendiente
        return repository.save(loanRequest);
    }

    /**
     * Devuelve todas las solicitudes de préstamo almacenadas.
     *
     * @return lista de todas las solicitudes
     */
    @Override
    public List<LoanRequest> getAllLoanRequests() {
        return repository.findAll();
    }

    /**
     * Busca una solicitud por su ID.
     *
     * @param id ID de la solicitud
     * @return Optional con la solicitud si existe, vacío si no
     */
    @Override
    public Optional<LoanRequest> getLoanRequestById(Long id) {
        return repository.findById(id);
    }

    /**
     * Cambia el estado de una solicitud de préstamo respetando el flujo permitido:
     * - PENDIENTE -> APROBADA / RECHAZADA
     * - APROBADA -> CANCELADA
     *
     * @param id ID de la solicitud
     * @param newStatus nuevo estado a asignar
     * @return la solicitud actualizada
     * @throws InvalidLoanStatusTransitionException si el cambio de estado no es válido
     */
    @Override
    public LoanRequest updateLoanRequestStatus(Long id, LoanStatus newStatus) {
        LoanRequest loan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("LoanRequest not found"));

        // Validación del flujo de estados
        LoanStatus current = loan.getStatus();
        if (current == LoanStatus.PENDIENTE
                && (newStatus == LoanStatus.APROBADA || newStatus == LoanStatus.RECHAZADA)) {
            loan.setStatus(newStatus);
        } else if (current == LoanStatus.APROBADA && newStatus == LoanStatus.CANCELADA) {
            loan.setStatus(newStatus);
        } else {
            throw new InvalidLoanStatusTransitionException("Invalid status transition from " + current + " to " + newStatus);
        }

        repository.update(loan);
        return loan;
    }
}
