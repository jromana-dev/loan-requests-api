# Loan Requests API

API REST sencilla para gestionar solicitudes de préstamos personales.  
Permite a los clientes crear solicitudes de préstamo, a los gestores actualizar su estado y consultar el historial de solicitudes.

---

## 1. Instrucciones para ejecutar el proyecto

### Requisitos

- **Java 17** o superior
- **Maven 3.8+**
- IDE recomendado: Visual Studio Code, IntelliJ IDEA o Eclipse

### Clonar el repositorio

```bash
git clone https://github.com/jromana-dev/loan-requests-api.git
cd loan-requests-api
```

### Ejecutar la aplicación

```bash
mvn clean install
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

### Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/loans` | Crear una nueva solicitud de préstamo |
| GET | `/loans` | Listar todas las solicitudes |
| GET | `/loans/{id}` | Obtener una solicitud específica |
| PATCH | `/loans/{id}/status` | Actualizar el estado de una solicitud |

### Ejecutar tests

```bash
mvn test
```

### Colección de Postman

Se incluye una colección de Postman para probar la API:

```
/docs/postman/LoanRequestsAPI.postman_collection.json
```

### Ejemplos de request/responses

#### Crear solicitud de préstamo (POST /loans)

**Request:**

```json
{
  "applicantName": "Carlos",
  "amount": 1000,
  "currency": "EUR",
  "documentId": "12345678Z",
  "creationDate": "2026-01-20"
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "applicantName": "Carlos",
  "amount": 1000,
  "currency": "EUR",
  "documentId": "12345678Z",
  "creationDate": "2026-01-20",
  "status": "PENDIENTE"
}
```

#### Actualizar estado de préstamo (PATCH /loans/1/status)

**Request:**

```json
{
  "newStatus": "APROBADA"
}
```

**Response (200 OK):**

```json
{
  "id": 1,
  "applicantName": "Carlos",
  "amount": 1000,
  "currency": "EUR",
  "documentId": "12345678Z",
  "creationDate": "2026-01-20",
  "status": "APROBADA"
}
```

**Response al intentar transición inválida (400 Bad Request):**

```json
{
  "error": "Invalid status transition from APROBADA to RECHAZADA"
}
```

---

## 2. Arquitectura y decisiones técnicas

- **Spring Boot 4.0.1** como framework principal
- **Java 17** para compatibilidad moderna y seguridad de tipos
- **Estructura de paquetes** mínima recomendada:
  - `controller` → Endpoints REST
  - `service` → Lógica de negocio, gestión de solicitudes
  - `repository` → Repositorio in-memory para CRUD de préstamos
  - `domain` → Entidades y enums (LoanRequest, LoanStatus)
  - `dto` → Objetos de transferencia para requests y responses
  - `exception` → Excepciones específicas (p.ej. InvalidLoanStatusTransitionException)

- **Repositorio in-memory**: no persistente, adecuado para prueba rápida de 48h
- **DTOs**: separan la entrada/salida de la entidad interna
- **Tests unitarios y de integración**:
  - Servicio (LoanRequestServiceImpl)
  - Controller (LoanRequestController) usando MockMvc
  - Repositorio (LoanRequestRepository)
  - Todos los tests están comentados profesionalmente

- **Gestión de estados de préstamo**:
  - Flujo permitido: `PENDIENTE` → `APROBADA` | `RECHAZADA` y `APROBADA` → `CANCELADA`
  - Se lanza `InvalidLoanStatusTransitionException` para transiciones inválidas

- **Maven**: manejo de dependencias y build
- **.gitignore**: configurado para excluir `target/` y archivos de IDE

---

## 3. Mejoras o extensiones con más tiempo

### Funcionales

- Persistencia en base de datos (MySQL, PostgreSQL o H2 en memoria)
- Implementar autenticación y autorización (Spring Security) para diferenciar clientes y gestores
- Validaciones más completas de los datos de entrada (DNI/NIE, importe, divisa, fechas)
- Endpoint de búsqueda y filtrado de solicitudes (por estado, fecha o solicitante)
- Paginación y ordenación en consultas de solicitudes
- API documentada con Swagger / OpenAPI

### Técnicas / arquitecturales

- Separar la capa de persistencia y lógica de negocio con interfaces para facilitar testing y escalabilidad
- Integrar logging y monitoreo (SLF4J + Actuator / Micrometer)
- Añadir manejo global de excepciones (@ControllerAdvice) para respuestas uniformes
- Implementar CI/CD con GitHub Actions para build, tests y despliegue automático
- Uso de contenedores Docker para aislar y desplegar la API