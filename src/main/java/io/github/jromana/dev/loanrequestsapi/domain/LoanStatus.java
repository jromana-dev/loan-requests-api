package io.github.jromana.dev.loanrequestsapi.domain;

/**
 * Enum que representa los posibles estados de una solicitud de préstamo.
 * Flujo permitido:
 *   - PENDIENTE -> APROBADA / RECHAZADA
 *   - APROBADA -> CANCELADA
 */

public enum LoanStatus {
    PENDIENTE,
    APROBADA,
    RECHAZADA,
    CANCELADA
}
