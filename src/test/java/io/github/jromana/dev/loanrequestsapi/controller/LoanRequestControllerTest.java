package io.github.jromana.dev.loanrequestsapi.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.github.jromana.dev.loanrequestsapi.domain.LoanStatus;
import io.github.jromana.dev.loanrequestsapi.dto.CreateLoanRequestDTO;
import io.github.jromana.dev.loanrequestsapi.dto.UpdateLoanStatusDTO;
import io.github.jromana.dev.loanrequestsapi.service.LoanRequestService;
import tools.jackson.databind.ObjectMapper;

class LoanRequestControllerTest {

    private MockMvc mockMvc;
    private LoanRequestService service;
    private LoanRequestController controller;
    private ObjectMapper objectMapper;

    /**
     * Configura el entorno de pruebas antes de cada test:
     * - Crea un mock manual del servicio LoanRequestService.
     * - Inicializa el controller con el mock.
     * - Construye MockMvc para testear endpoints sin levantar el contexto completo de Spring.
     * - Inicializa ObjectMapper para serializar DTOs a JSON.
     */
    @BeforeEach
    void setup() {
        service = mock(LoanRequestService.class); // Mock manual con Mockito
        controller = new LoanRequestController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Verifica que la API POST /loans crea correctamente un nuevo préstamo.
     * Se espera un código HTTP 201 Created como respuesta.
     */
    @Test
    void testCreateLoan() throws Exception {
        CreateLoanRequestDTO dto = new CreateLoanRequestDTO();
        dto.setApplicantName("Carlos");
        dto.setAmount(1000);
        dto.setCurrency("EUR");
        dto.setDocumentId("12345678Z");
        dto.setCreationDate(LocalDate.now());

        mockMvc.perform(post("/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    /**
     * Verifica que la API PATCH /loans/{id}/status permite actualizar
     * correctamente el estado de un préstamo a APROBADA.
     * Se espera un código HTTP 200 OK como respuesta.
     */
    @Test
    void testUpdateLoanStatus() throws Exception {
        UpdateLoanStatusDTO dto = new UpdateLoanStatusDTO();
        dto.setNewStatus(LoanStatus.APROBADA);

        mockMvc.perform(patch("/loans/1/status")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    /**
     * Verifica que la API PATCH /loans/{id}/status devuelve un error
     * cuando se intenta realizar una transición de estado inválida
     * (por ejemplo, PENDIENTE -> CANCELADA directamente).
     * Se espera un código HTTP 400 Bad Request como respuesta.
     */
    @Test
    void testUpdateLoanStatusInvalid() throws Exception {
        UpdateLoanStatusDTO dto = new UpdateLoanStatusDTO();
        dto.setNewStatus(LoanStatus.CANCELADA);

        mockMvc.perform(patch("/loans/1/status")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
