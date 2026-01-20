package io.github.jromana.dev.loanrequestsapi.service;

import java.util.List;
import java.util.Optional;

import io.github.jromana.dev.loanrequestsapi.domain.LoanRequest;
import io.github.jromana.dev.loanrequestsapi.domain.LoanStatus;

/**
 * Servicio para gestionar la lógica de negocio de las solicitudes de préstamo.
 */
public interface LoanRequestService {

    /**
     * Crea una nueva solicitud de préstamo.
     */
    LoanRequest createLoanRequest(LoanRequest loanRequest);

    /**
     * Obtiene todas las solicitudes de préstamo.
     */
    List<LoanRequest> getAllLoanRequests();

    /**
     * Obtiene una solicitud por su ID.
     */
    Optional<LoanRequest> getLoanRequestById(Long id);

    /**
     * Cambia el estado de una solicitud de préstamo, respetando el flujo permitido.
     */
    LoanRequest updateLoanRequestStatus(Long id, LoanStatus newStatus);
    
}
