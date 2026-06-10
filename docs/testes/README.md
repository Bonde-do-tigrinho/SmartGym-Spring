# Testes de Unidade - SmartGym

Este diretorio centraliza os artefatos de teste do caso de uso `FichaTreinoUseCase`.

## Links

- Codigo dos testes (JUnit):
  - `src/test/kotlin/com/academia/smartgym/application/usecases/FichaTreinoUseCaseTest.kt`
  - GitHub: https://github.com/Bonde-do-tigrinho/SmartGym-Spring/blob/Kendas-main-aux/src/test/kotlin/com/academia/smartgym/application/usecases/FichaTreinoUseCaseTest.kt
- Relatorio de execucao e cobertura (JaCoCo):
  - `docs/testes/ficha-treino-relatorio.md`
  - GitHub: https://github.com/Bonde-do-tigrinho/SmartGym-Spring/blob/Kendas-main-aux/docs/testes/ficha-treino-relatorio.md

## Comandos usados na execucao

```powershell
.\mvnw.cmd -Dtest=FichaTreinoUseCaseTest clean verify
```

## Relatorios gerados localmente pelo Maven

- Surefire (resultado de testes): `target/surefire-reports/com.academia.smartgym.application.usecases.FichaTreinoUseCaseTest.txt`
- JaCoCo (HTML): `target/site/jacoco/index.html`
- JaCoCo (XML): `target/site/jacoco/jacoco.xml`
- JaCoCo (CSV): `target/site/jacoco/jacoco.csv`

