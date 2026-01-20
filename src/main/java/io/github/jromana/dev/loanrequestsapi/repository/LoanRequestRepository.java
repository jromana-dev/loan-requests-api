package io.github.jromana.dev.loanrequestsapi.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.github.jromana.dev.loanrequestsapi.domain.LoanRequest;

/**
 * Repositorio en memoria para gestionar solicitudes de préstamo.
 * Permite crear, actualizar y consultar solicitudes sin usar base de datos.
 */

@Repository
public class LoanRequestRepository {
    private final Map<Long, LoanRequest> storage = new HashMap<>();
    private Long counter = 1L; // Contador simple para generar IDs únicos

    /**
     * Guarda una nueva solicitud y le asigna un ID único.
     */
    public LoanRequest save(LoanRequest loanRequest) {
        loanRequest.setId(counter++);
        storage.put(loanRequest.getId(), loanRequest);
        return loanRequest;
    }

    /**
     * Busca una solicitud por su ID.
     */
    public Optional<LoanRequest> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Devuelve todas las solicitudes almacenadas.
     */
    public List<LoanRequest> findAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Actualiza una solicitud existente.
     */
    public void update(LoanRequest loanRequest) {
        storage.put(loanRequest.getId(), loanRequest);
    }
}
