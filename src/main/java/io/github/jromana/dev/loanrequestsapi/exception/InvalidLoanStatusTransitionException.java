package io.github.jromana.dev.loanrequestsapi.exception;

/**
 * Excepción lanzada cuando se intenta cambiar el estado de una solicitud de
 * préstamo de manera no permitida según el flujo definido.
 */
public class InvalidLoanStatusTransitionException extends RuntimeException {

    public InvalidLoanStatusTransitionException(String message) {
        super(message);
    }

}
