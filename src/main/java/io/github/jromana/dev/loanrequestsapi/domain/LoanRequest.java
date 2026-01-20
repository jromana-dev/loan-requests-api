package io.github.jromana.dev.loanrequestsapi.domain;

import java.time.LocalDate;

/**
 * Representa una solicitud de préstamo realizada por un cliente.
 * Contiene datos básicos como nombre del solicitante, importe, divisa,
 * documento identificativo y estado actual de la solicitud.
 */

public class LoanRequest {

    private Long id; // Identificador único de la solicitud
    private String applicantName; // Nombre del solicitante
    private double amount; // Importe solicitado
    private String currency; // Divisa del importe
    private String documentId; // Documento identificativo del solicitante (DNI, NIE, etc.)
    private LocalDate creationDate; // Fecha de creación de la solicitud
    private LoanStatus status; // Estado actual de la solicitud 

    // Constructores
    
    public LoanRequest() {
    }

    public LoanRequest(Long id, String applicantName, double amount, String currency, String documentId, LocalDate creationDate, LoanStatus status) {
        this.id = id;
        this.applicantName = applicantName;
        this.amount = amount;
        this.currency = currency;
        this.documentId = documentId;
        this.creationDate = creationDate;
        this.status = status;
    }

    // Getters y Setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantName() {
        return this.applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
