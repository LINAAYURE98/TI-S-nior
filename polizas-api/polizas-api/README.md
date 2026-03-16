# Polizas API — Prueba Técnica Desarrollador TI / Sénior

API REST para gestión de pólizas de arrendamiento construida con Spring Boot 3 y Java 17.

---

## Requisitos

- Java 17 o superior
- Maven 3.8+

---

## Cómo ejecutar

```bash
# 1. Clonar o descomprimir el proyecto
cd polizas-api

# 2. Compilar y ejecutar
./mvnw spring-boot:run

# En Windows:
mvnw.cmd spring-boot:run
```

El servidor inicia en: `http://localhost:8080`

---

## Seguridad

Todos los endpoints requieren el header:

```
x-api-key: 123456
```

Sin este header la API responde `401 Unauthorized`.

---

## Endpoints disponibles

### Pólizas

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/polizas` | Listar pólizas. Filtros opcionales: `?tipo=INDIVIDUAL&estado=ACTIVA` |
| GET | `/polizas/{id}/riesgos` | Listar riesgos de una póliza |
| POST | `/polizas/{id}/renovar` | Renovar póliza aplicando IPC |
| POST | `/polizas/{id}/cancelar` | Cancelar póliza y sus riesgos |
| POST | `/polizas/{id}/riesgos` | Agregar riesgo (solo COLECTIVA) |

### Riesgos

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/riesgos/{id}/cancelar` | Cancelar un riesgo |

### CORE Mock

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/core-mock/evento` | Simula envío de evento al CORE |

---

## Ejemplos de uso

### Listar todas las pólizas
```http
GET http://localhost:8080/polizas
x-api-key: 123456
```

### Listar pólizas COLECTIVAS activas
```http
GET http://localhost:8080/polizas?tipo=COLECTIVA&estado=ACTIVA
x-api-key: 123456
```

### Renovar póliza
```http
POST http://localhost:8080/polizas/1/renovar
x-api-key: 123456
```

### Cancelar póliza
```http
POST http://localhost:8080/polizas/1/cancelar
x-api-key: 123456
```

### Agregar riesgo a póliza COLECTIVA
```http
POST http://localhost:8080/polizas/2/riesgos
x-api-key: 123456
Content-Type: application/json

{
  "descripcion": "Apartamento 301 - Torre B",
  "arrendatario": "Juan Díaz"
}
```

### Cancelar riesgo
```http
POST http://localhost:8080/riesgos/2/cancelar
x-api-key: 123456
```

### Enviar evento al CORE mock
```http
POST http://localhost:8080/core-mock/evento
x-api-key: 123456
Content-Type: application/json

{
  "evento": "ACTUALIZACION",
  "polizaId": 555
}
```

---

## Reglas de negocio implementadas

- Una póliza **INDIVIDUAL** solo puede tener 1 riesgo activo.
- No se puede renovar una póliza en estado **CANCELADA**.
- Cancelar una póliza cancela automáticamente **todos sus riesgos**.
- Solo se pueden agregar riesgos a pólizas de tipo **COLECTIVA**.
- La renovación incrementa canon y prima según el **IPC (9.7%)**.

---

## Consola H2 (base de datos en memoria)

Disponible en: `http://localhost:8080/h2-console`

```
JDBC URL:  jdbc:h2:mem:polizasdb
Usuario:   sa
Password:  (vacío)
```

---

## Estructura del proyecto

```
src/main/java/com/example/polizasapi/
├── controller/
│   ├── PolizaController.java
│   ├── RiesgoController.java
│   └── CoreMockController.java
├── service/
│   ├── PolizaService.java
│   └── RiesgoService.java
├── repository/
│   ├── PolizaRepository.java
│   └── RiesgoRepository.java
├── entity/
│   ├── Poliza.java
│   └── Riesgo.java
├── security/
│   └── ApiKeyFilter.java
├── exception/
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java
└── PolizasApiApplication.java
```
