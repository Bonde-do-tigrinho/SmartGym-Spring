# Relatorio de Testes - FichaTreinoUseCase

## 1) Escopo e abordagem

Este relatorio apresenta os testes de unidade do caso de uso `FichaTreinoUseCase`, com foco em:

- classe de controle (service/use case): `com.academia.smartgym.application.usecases.FichaTreinoUseCase`
- entidades/modelos de dominio envolvidos no caso: `FichaTreino`, `ExercicioFichaTreino`, `Usuario`, `Exercicio`
- regras de negocio da validacao e persistencia

Tecnica aplicada: **teste estrutural (caixa-branca)**, cobrindo fluxos de sucesso e erro (ramos condicionais).

Ferramentas utilizadas:

- **JUnit 5** para implementacao dos testes
- **Mockito** para mocks dos repositorios
- **JaCoCo** para cobertura de codigo

## 2) Organizacao do codigo de testes

- Pacote: `src/test/kotlin/com/academia/smartgym/application/usecases`
- Classe de teste: `FichaTreinoUseCaseTest`
- Arquivo: `src/test/kotlin/com/academia/smartgym/application/usecases/FichaTreinoUseCaseTest.kt`

Uso de mocks explicitado por comentarios no proprio codigo:

- mock de `FichaTreinoRepository`
- mock de `UsuarioRepository`
- mock de `ExercicioRepository`

## 3) Execucao dos testes

Comando executado:

```powershell
Set-Location "C:\Users\kendi\Documents\Github\SmartGym-Spring"
.\mvnw.cmd -Dtest=FichaTreinoUseCaseTest clean verify
```

Evidencia de resultado (Surefire):

- `Tests run: 16`
- `Failures: 0`
- `Errors: 0`
- `Skipped: 0`
- `BUILD SUCCESS`

Arquivo de evidencia:

- `target/surefire-reports/com.academia.smartgym.application.usecases.FichaTreinoUseCaseTest.txt`

## 4) Cobertura de testes (JaCoCo)

Relatorios gerados:

- HTML: `target/site/jacoco/index.html`
- XML: `target/site/jacoco/jacoco.xml`
- CSV: `target/site/jacoco/jacoco.csv`

### 4.1 Cobertura por pacote (recorte do caso de uso)

| Pacote | Instruction | Branch | Line |
|---|---:|---:|---:|
| `com.academia.smartgym.application.usecases` | 12.48% | 17.65% | 11.43% |

> Observacao: esse percentual do pacote inclui varios use cases nao exercitados nesta execucao focada apenas em `FichaTreinoUseCaseTest`.

### 4.2 Cobertura por classe (caso de uso selecionado)

| Pacote | Classe | Instruction | Branch | Line |
|---|---|---:|---:|---:|
| `com.academia.smartgym.application.usecases` | `FichaTreinoUseCase` | 100.00% | 100.00% | 100.00% |

### 4.3 Cobertura por classe (entidades/modelos vinculados)

| Pacote | Classe | Instruction | Branch | Line |
|---|---|---:|---:|---:|
| `com.academia.smartgym.domain.model` | `FichaTreino` | 70.91% | N/A | 85.71% |
| `com.academia.smartgym.domain.model` | `ExercicioFichaTreino` | 100.00% | N/A | 100.00% |
| `com.academia.smartgym.domain.model` | `Usuario` | 72.94% | N/A | 80.95% |
| `com.academia.smartgym.domain.model` | `Exercicio` | 69.44% | N/A | 66.67% |

## 5) Regras de negocio validadas

Os testes cobrem, entre outros, os seguintes cenarios:

- busca por id com sucesso e excecao quando nao encontrado
- listagem geral e por aluno
- deduplicacao de exercicios por `exercicioId` antes de salvar
- validacao de aluno existente
- validacao de lista de exercicios nao vazia
- validacao de `series > 0`, `repeticoes > 0`, `descansoSegundos >= 0`
- validacao de exercicios referenciados existentes
- exclusao por id

## 6) Links para a entrega

- Link do codigo dos testes implementados:
  - https://github.com/Bonde-do-tigrinho/SmartGym-Spring/blob/Kendas-main-aux/src/test/kotlin/com/academia/smartgym/application/usecases/FichaTreinoUseCaseTest.kt
- Link do relatorio dos testes executados:
  - https://github.com/Bonde-do-tigrinho/SmartGym-Spring/blob/Kendas-main-aux/docs/testes/ficha-treino-relatorio.md

