package io.github.jromana.dev.loanrequestsapi.dto;

import io.github.jromana.dev.loanrequestsapi.domain.LoanStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO usado para actualizar el estado de una solicitud.
 */
public class UpdateLoanStatusDTO {

    @NotNull(message = "New status must be provided")
    private LoanStatus newStatus;

    // Getter y setter

    public LoanStatus getNewStatus() {
        return this.newStatus;
    }

    public void setNewStatus(LoanStatus newStatus) {
        this.newStatus = newStatus;
    }
    
}
