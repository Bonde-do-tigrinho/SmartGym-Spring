# AGENTS.md - SmartGym Spring Guide

## Project Overview

**SmartGym** is a Spring Boot REST API for managing fitness equipment (máquinas) in a gym. It's a Kotlin-based application using Spring Boot 4.0.5 with PostgreSQL and follows **Clean Architecture** principles with strict separation of concerns.

**Key Stack:** Kotlin 2.2.21, Spring Boot 4.0.5, JPA/Hibernate, PostgreSQL 15+, Maven

## Architecture Pattern: Clean Architecture with Dependency Injection

The project enforces **strict layering** across 4 tiers. Data flows inward; domain knows nothing of infrastructure:

```
API Layer (Controller) 
  ↓ (uses)
Application Layer (UseCase/Service)
  ↓ (depends on abstraction)
Domain Layer (Model + Repository interface)
  ↓ (implemented by)
Infrastructure Layer (Entity, JPA Repo, Mapper)
```

### Critical Design: Domain Purity vs. Infrastructure Reality

- **Domain Model** (`Maquina.kt`): Pure Kotlin data class with `StatusMaquina` enum (LIVRE, OCUPADA, MANUTENCAO)
- **Persistence Entity** (`MaquinaEntity.kt`): JPA-mapped class with `@Entity`, stores `status` as String in DB
- **Adapter Pattern**: `MaquinaMapper` converts between domain and persistence layers (entity ↔ domain object)
- **Repository Interface**: Defined in domain, implemented in infrastructure as Spring `JpaRepository` adapter

**Why this matters:** When modifying the `Maquina` model, changes must propagate through mapper → entity mapping. New fields need entity column definitions AND mapper conversions.

## Build & Runtime Environment

### Build Commands
```bash
# Maven wrapper (cross-platform)
./mvnw clean package        # Build JAR
./mvnw test                 # Run tests
./mvnw spring-boot:run      # Run application locally
```

### Database Setup (Required!)
```bash
# Start PostgreSQL container
docker-compose up -d

# Auto-migrations handled by Hibernate (ddl-auto: update in application.yml)
# Connection: localhost:5433 (NOT 5432) | user: admin | password: admin
```

**Critical Config** (`application.yml`): 
- `show-sql: true` - All SQL logged to console (verbose but helpful for debugging)
- `format_sql: true` - Pretty-prints SQL
- `ddl-auto: update` - Auto-creates/updates tables from entities on startup

## Package Structure & Responsibilities

| Package | Purpose | Key Files |
|---------|---------|-----------|
| `domain/model/` | Business logic, enums, DTOs | `Maquina.kt`, `StatusMaquina` enum |
| `domain/repository/` | Repository contracts (abstraction) | `MaquinaRepository` interface |
| `application/usecases/` | Business workflows, Spring services | `MaquinaUseCase.kt` (CRUD logic) |
| `infrastructure/api/controller/` | HTTP endpoints | `MaquinaController.kt` (REST mappings) |
| `infrastructure/persistence/entities/` | JPA entities | `MaquinaEntity.kt` (DB schema) |
| `infrastructure/persistence/mappers/` | Domain ↔ Entity conversion | `MaquinaMapper.kt` (conversion logic) |
| `infrastructure/persistence/repositories/` | Spring JPA implementations | `SpringMaquinaRepository` (DB queries) |

## Key Development Patterns

### Kotlin-Specific Patterns Used
- **Data Classes**: `Maquina` is a data class with default parameters
- **Extension Functions**: Mappers use Kotlin extension functions (`MaquinaEntity.toDomain()`)
- **Named Parameters**: Controller uses `maquina.copy(id = id)` for immutable updates
- **Spring Plugin Optimization**: `kotlin-maven-plugin` configured with `all-open` to auto-open classes for Spring/JPA proxying

### REST Endpoint Pattern (MaquinaController)
```
GET    /api/maquinas           → list all
GET    /api/maquinas/{id}      → get by ID
POST   /api/maquinas           → create (body: Maquina JSON)
PUT    /api/maquinas/{id}      → update (creates copy with new ID)
DELETE /api/maquinas/{id}      → delete
```

### Error Handling Convention
Currently uses basic exception throwing: `throw Exception("Máquina não encontrada")` in UseCase. This should be replaced with custom exceptions and proper HTTP error responses.

## Known Quirks & Gotchas

1. **Package Name Mismatch**: Domain/mapper imports reference `com.example.smartgym` while actual package is `com.academia.smartgym`. This is a bug that causes compilation issues. **Always use `com.academia.smartgym` for new code.**

2. **Status as String in DB**: The enum `StatusMaquina` is stored as String in the database (`status.name` → "LIVRE"/"OCUPADA"/"MANUTENCAO"). Mapper handles conversion. Changing enum values requires DB migration.

3. **Lombok Excluded from Spring Boot Plugin**: Kotlin uses data classes instead, but Lombok is in dependencies (likely legacy).

4. **Missing Test Structure**: Test directory exists but is empty. New features should include unit/integration tests in `src/test/kotlin/`.

## Adding New Features: Step-by-Step Template

To add a new entity (e.g., "Instrutor"):

1. **Domain Model** (`domain/model/Instrutor.kt`):
   ```kotlin
   data class Instrutor(val id: Long? = null, val nome: String, ...)
   ```

2. **Repository Interface** (`domain/repository/InstrutorRepository.kt`):
   ```kotlin
   interface InstrutorRepository { fun findAll(): List<Instrutor> ... }
   ```

3. **JPA Entity** (`infrastructure/persistence/entities/InstrutorEntity.kt`):
   ```kotlin
   @Entity @Table(name = "instrutores")
   class InstrutorEntity(@Id @GeneratedValue val id: Long?, ...)
   ```

4. **Mapper** (`infrastructure/persistence/mappers/InstrutorMapper.kt`):
   ```kotlin
   class InstrutorMapper {
       fun InstrutorEntity.toDomain() = Instrutor(...)
       fun Instrutor.toEntity() = InstrutorEntity(...)
   }
   ```

5. **Spring Repository** (`infrastructure/persistence/repositories/SpringInstrutorRepository.kt`):
   ```kotlin
   @Repository
   interface SpringInstrutorRepository : JpaRepository<InstrutorEntity, Long>
   ```

6. **UseCase** (`application/usecases/InstrutorUseCase.kt`):
   ```kotlin
   @Service
   class InstrutorUseCase(private val repository: InstrutorRepository)
   ```

7. **Controller** (`infrastructure/api/controller/InstrutorController.kt`):
   ```kotlin
   @RestController @RequestMapping("/api/instrutores")
   class InstrutorController(private val useCase: InstrutorUseCase)
   ```

**Critical:** Update the Spring repository implementation to implement the domain interface for dependency injection.

## Testing Strategy

- Use `kotlin-test-junit5` for unit tests
- Integration tests should mock `MaquinaRepository` in UseCase layer
- No existing test examples; follow standard Spring Boot testing patterns with `@SpringBootTest` and `@MockBean`

## Common Commands for Development

```bash
# Start dev environment
docker-compose up -d && ./mvnw spring-boot:run

# Check compilation errors
./mvnw compile

# Format code (Kotlin standards)
./mvnw kotlinFormat:format

# View logs
docker logs -f postgres-smartgym   # Database logs
```

