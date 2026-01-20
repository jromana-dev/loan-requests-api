package io.github.jromana.dev.loanrequestsapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.jromana.dev.loanrequestsapi.domain.LoanRequest;
import io.github.jromana.dev.loanrequestsapi.dto.CreateLoanRequestDTO;
import io.github.jromana.dev.loanrequestsapi.dto.UpdateLoanStatusDTO;
import io.github.jromana.dev.loanrequestsapi.exception.InvalidLoanStatusTransitionException;
import io.github.jromana.dev.loanrequestsapi.service.LoanRequestService;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar solicitudes de préstamo.
 */
@RestController
@RequestMapping("/loans")
public class LoanRequestController {

    // Servicio inyectado para la lógica de negocio

    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    /**
     * Crear una nueva solicitud de préstamo.
     */
    @PostMapping
    public ResponseEntity<LoanRequest> createLoan(@Valid @RequestBody CreateLoanRequestDTO dto) {
        LoanRequest loan = new LoanRequest();
        loan.setApplicantName(dto.getApplicantName());
        loan.setAmount(dto.getAmount());
        loan.setCurrency(dto.getCurrency());
        loan.setDocumentId(dto.getDocumentId());
        loan.setCreationDate(dto.getCreationDate());

        LoanRequest createdLoan = loanRequestService.createLoanRequest(loan);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    /**
     * Listar todas las solicitudes de préstamo.
     */
    @GetMapping
    public List<LoanRequest> getAllLoans() {
        return loanRequestService.getAllLoanRequests();
    }

    /**
     * Consultar una solicitud por su ID.
     */
    @GetMapping("/{id}")
    public LoanRequest getLoanById(@PathVariable Long id) {
        return loanRequestService.getLoanRequestById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LoanRequest not found"));
    }

    /**
     * Actualizar el estado de una solicitud.
     */
    @PatchMapping("/{id}/status")
    public LoanRequest updateLoanStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLoanStatusDTO dto) {
        try {
            return loanRequestService.updateLoanRequestStatus(id, dto.getNewStatus());
        } catch (InvalidLoanStatusTransitionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
}
