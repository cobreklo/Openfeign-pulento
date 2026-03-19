# 🏟️ Club Deportivo — Microservicios con Spring Cloud OpenFeign

Sistema de gestión de canchas deportivas y reservas, implementado como **dos microservicios independientes** que se comunican entre sí usando **Spring Cloud OpenFeign**.

---

## 📡 ¿Qué es Spring Cloud OpenFeign?

**Spring Cloud OpenFeign** es un cliente HTTP declarativo que facilita la comunicación entre microservicios dentro de un ecosistema Spring Boot. En vez de escribir código repetitivo para hacer peticiones HTTP (como con `RestTemplate` o `WebClient`), OpenFeign nos permite definir una simple **interfaz Java** con anotaciones, y el framework se encarga del resto.

### ¿Cómo funciona?

La idea es bastante simple y elegante:

1. **Definimos una interfaz** con los métodos que representan las llamadas HTTP que queremos hacer.
2. **Anotamos la interfaz** con `@FeignClient`, indicando el nombre y la URL del servicio destino.
3. **Usamos anotaciones de Spring MVC** (`@GetMapping`, `@PostMapping`, etc.) en cada método para especificar la ruta y el verbo HTTP.
4. **Spring genera automáticamente una implementación** en tiempo de ejecución que hace las peticiones HTTP reales.

### Ventajas de usar OpenFeign

- ✅ **Código limpio y declarativo**: No hay que escribir lógica HTTP manualmente.
- ✅ **Integración total con Spring**: Se comporta como cualquier otro bean, se puede inyectar con `@Autowired`.
- ✅ **Fácil de mantener**: Si cambia un endpoint en el servicio proveedor, solo hay que actualizar la interfaz.
- ✅ **Manejo de errores integrado**: Soporta Circuit Breakers y fallbacks si se combina con Resilience4j.
- ✅ **Serialización automática**: Convierte JSON a objetos Java y viceversa sin configuración extra.

### Ejemplo visual del flujo

```
┌─────────────────────┐         HTTP GET          ┌─────────────────────┐
│                     │  ───────────────────────>  │                     │
│   Servicio Reservas │    /api/canchas/{id}       │  Servicio Canchas   │
│     (puerto 8082)   │  <───────────────────────  │    (puerto 8081)    │
│                     │      Respuesta JSON        │                     │
└─────────────────────┘                            └─────────────────────┘
        │                                                    │
   CanchaFeignClient                                  CanchaController
   (interfaz Feign)                                   (REST Controller)
```

---

## 🔧 Cambios implementados en el proyecto

### Servicio de Canchas (`serviciocanchas` — puerto 8081)

> **No requiere cambios.** Este servicio actúa como proveedor de datos a través de su API REST existente. OpenFeign consume sus endpoints desde el servicio de Reservas.

### Servicio de Reservas (`servicioreservas` — puerto 8082)

Los cambios se implementaron exclusivamente en este servicio, que es el **consumidor** de la API de Canchas:

#### 1. Dependencias (`pom.xml`)

Se agregó el BOM de Spring Cloud y la dependencia de OpenFeign:

```xml
<!-- BOM de Spring Cloud para gestionar versiones -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2025.1.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<!-- Dependencia de OpenFeign -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### 2. Habilitación de Feign (`ServicioreservasApplication.java`)

Se agregó la anotación `@EnableFeignClients` en la clase principal para que Spring pueda detectar y crear las implementaciones de los clientes Feign:

```java
@SpringBootApplication
@EnableFeignClients  // <-- Activa el escaneo de interfaces Feign
public class ServicioreservasApplication { ... }
```

#### 3. Cliente Feign (`CanchaFeignClient.java`) — **ARCHIVO NUEVO**

Se creó una interfaz declarativa que define cómo comunicarse con el servicio de Canchas:

```java
@FeignClient(name = "serviciocanchas", url = "http://localhost:8081")
public interface CanchaFeignClient {

    @GetMapping("/api/canchas")
    List<Cancha> listarCanchas();

    @GetMapping("/api/canchas/{id}")
    Cancha obtenerCanchaPorId(@PathVariable("id") Long id);
}
```

#### 4. Capa de servicios (`ReservaServices.java` y `ReservaServicesImpl.java`)

Se agregaron dos nuevos métodos que usan el cliente Feign:

- `obtenerCanchaDeReserva(Long canchaId)` — consulta una cancha por ID.
- `listarCanchasDisponibles()` — trae todas las canchas desde el otro microservicio.

#### 5. Controlador (`ReservaController.java`)

Se agregaron dos endpoints nuevos que exponen la comunicación inter-servicio:

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/reservas/canchas` | Lista todas las canchas (vía OpenFeign) |
| `GET` | `/api/reservas/canchas/{canchaId}` | Obtiene una cancha por ID (vía OpenFeign) |

---

## 📋 Requisitos previos

- **Java 21** o superior
- **Maven** (incluido como wrapper: `mvnw`)
- **MySQL** con una base de datos llamada `club_deportivo`
- **Puertos 8081 y 8082** disponibles

---

## 🚀 Instrucciones de ejecución

### 1. Configurar la base de datos

Asegúrate de tener MySQL corriendo con una base de datos `club_deportivo`. Las tablas se crean automáticamente gracias a `spring.jpa.hibernate.ddl-auto=update`.

### 2. Iniciar el Servicio de Canchas (primero)

```bash
cd serviciocanchas
./mvnw spring-boot:run
```

El servicio estará disponible en `http://localhost:8081`.

### 3. Iniciar el Servicio de Reservas

```bash
cd servicioreservas
./mvnw spring-boot:run
```

El servicio estará disponible en `http://localhost:8082`.

### 4. Probar la comunicación entre servicios

```bash
# Crear una cancha en el servicio de Canchas
curl -X POST http://localhost:8081/api/canchas \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Cancha Principal", "tipo": "Fútbol", "precioPorHora": 15000}'

# Consultar las canchas desde el servicio de Reservas (via OpenFeign)
curl http://localhost:8082/api/reservas/canchas

# Crear una reserva
curl -X POST http://localhost:8082/api/reservas \
  -H "Content-Type: application/json" \
  -d '{"canchaId": 1, "nombreCliente": "Juan Pérez", "fecha": "2026-03-25", "horaInicio": "10:00", "horaFin": "11:00"}'

# Obtener la cancha de la reserva (via OpenFeign)
curl http://localhost:8082/api/reservas/canchas/1
```

---

## 📁 Estructura del proyecto

```
190326/
├── serviciocanchas/          # Microservicio de Canchas (puerto 8081)
│   └── src/main/java/com/clubdeportivo/serviciocanchas/
│       ├── controller/       # CanchaController (API REST)
│       ├── model/            # Entidad Cancha (JPA)
│       ├── repository/       # CanchaRepository (Spring Data)
│       └── services/         # Lógica de negocio
│
├── servicioreservas/         # Microservicio de Reservas (puerto 8082)
│   └── src/main/java/com/clubdeportivo2/servicioreservas/
│       ├── client/           # 🆕 CanchaFeignClient (OpenFeign)
│       ├── controller/       # ReservaController (API REST + endpoints Feign)
│       ├── model/            # Entidad Reserva + DTO Cancha
│       ├── repository/       # ReservaRepository (Spring Data)
│       └── services/         # Lógica de negocio con integración Feign
│
├── .gitignore
└── README.md                 # Este archivo
```

---

## 👥 Tecnologías utilizadas

- **Spring Boot 4.0.3**
- **Spring Cloud 2025.1.0 (Oakwood)**
- **Spring Cloud OpenFeign**
- **Spring Data JPA**
- **MySQL**
- **Lombok** (servicio de Canchas)
- **Maven**
- **Java 21**
