# SmartGym - Backend API

## 👥 Integrantes do Grupo

* **Gabriel Kendi Zanon Takeda** - RA: 1111392321014
* **Joao Gabriel Silva Maximiano** - RA: 1111392321025
* **Leandro Rodrigues de Melo** - RA: 1111392321007
* **Miguel Lemos Ramos** - RA: 1111392321023
* **Nicolas Kenzo Yanase** - RA: []
* **Raul dos Santos Araujo** - RA: 1111392321008

## 🏋️‍♂️ Tema Escolhido
Sistema integrado de gerenciamento de academias, fichas de treino, agendamentos de aulas coletivas e monitoramento telemétrico de ambiente via IoT.

## 📝 Descrição do Problema Resolvido
Academias de ginástica enfrentam dificuldades crônicas na gestão de fluxo de alunos em aulas coletivas (superlotação), falta de acompanhamento histórico centralizado de avaliações físicas e fichas de treino, além da ausência de automação no monitoramento climático das salas de exercício. 

O **SmartGym** resolve esses problemas unificando:
1. Um back-end robusto e seguro baseado em microsserviços/camadas.
2. Um aplicativo multiplataforma (KMP) para Alunos, Professores e Administradores.
3. Um dispositivo de telemetria IoT que monitora as condições ambientais das salas de treino e envia os dados em tempo real, auxiliando na manutenção preventiva do espaço físico.

---

O sistema conta com persistência relacional robusta em banco de dados PostgreSQL, mapeada através das seguintes entidades:

1. **UsuarioEntity (`usuarios`)**: Gerencia o cadastro centralizado de usuários, credenciais de segurança e controle de perfis de acesso (`ALUNO`, `PROFESSOR`, `ADMINISTRADOR`).
2. **PlanoEntity (`planos`)**: Configuração dos planos de matrícula disponíveis na academia (valores, vigências e regras de contrato).
3. **UnidadeEntity (`unidades`)**: Identifica as diferentes filiais ou unidades físicas da rede de academias.
4. **AulaColetivaEntity (`aulas_coletivas`)**: Armazena o cronograma, capacidade máxima e horários das turmas de aulas coletivas.
5. **AgendamentoEntity (`agendamentos`)**: Controla a reserva de vagas dos alunos nas aulas coletivas, evitando a superlotação do espaço.
6. **ExercicioEntity (`exercicios`)**: Cadastro base de exercícios físicos disponíveis para montar os treinos (ex: Supino, Agachamento).
7. **FichaTreinoEntity (`fichas_treino`)**: Vincula as rotinas de treinos e prazos de validade diretamente aos alunos.
8. **ExercicioFichaTreinoEntity (`ficha_treino_exercicios`)**: Tabela associativa (N:M) que gerencia os exercícios específicos de uma ficha, contendo as séries, repetições e cargas.
9. **AvaliacaoEntity (`avaliacoes`)**: Guarda os registros de anamnese e avaliações físicas dos alunos (Peso, % Gordura, IMC e notas do avaliador).
10. **MaquinaIotEntity (`maquinas_iot`)**: Armazena as leituras telemétricas de movimentação e presença enviadas via hardware/MQTT, indicando se os aparelhos ou estações de treino estão ocupados em tempo real.
11. **DispositivoIotEntity (`dispositivos_iot`)**: Cadastro e gerenciamento de inventário dos dispositivos físicos e equipamentos associados aos sensores.
12. **NotificacaoEntity(`maquinas`)**: Responsável pelo disparo e histórico de alertas, avisos de treinos e notificações internas do sistema.

---

## ⚙️ Variáveis de Ambiente Necessárias
Crie um arquivo `application-local.yml` na pasta resources do projeto backend ou configure-as no seu ambiente de execução:

```env
  jwt:
    secret: seu-token-secreto-para-gerar-o-jwt
  spring:
    mail:
      username: equipesmartgym@gmail.com
      password: kngwvufawxbvzoya
    security:
      user:
        name: admin
    datasource:
      url: jdbc:postgresql://localhost:5433/smartgym_db
      username: admin
      password: admin
```

🚀 Instruções para Execução
Pré-requisitos

  Java JDK 21 ou superior instalado.
  Maven configurado (ou use o wrapper incluso ./mvnw).

Passo a Passo

  Clone o repositório e acesse a pasta do projeto correspondente.
  Configure o Banco de Dados: Certifique-se de que o Postgres está rodando e com as credenciais batendo com o arquivo application.properties ou suas variáveis de ambiente.

  Compile o Projeto:
    ```Bash
    ./mvnw clean compile
    ```

  O nosso banco de dados esta hospedado no Docker entao precisa subir esse container
    ```Bash
        docker-compose up -d
      ```

  Execute a Aplicação Spring Boot:
    ```Bash
      ./mvnw spring-boot:run
    ```

  O servidor subirá por padrão na porta 8080 (http://localhost:8080).
  Simulação IoT (Opcional): Ao iniciar, o microsserviço MqttMetricasListener se conectará ao broker automaticamente. Execute o circuito correspondente no Wokwi para começar a injetar dados direto no banco de dados.

🔑 Exemplos de Usuários/Senhas para Teste (Seeders)

Utilize as credenciais abaixo para testar os níveis de acesso (Roles) no fluxo de login da API:
Perfil	E-mail de Teste	Senha Padrão
Administrador -	admin@smartgym.com	admin123
Professor	- lucas.mendes@smartgym.com	professor123
Aluno -	sera preciso fazer um cadastro para ter acesso de aluno


## 🤝 Divisão de Responsabilidades por Integrante

* **Gabriel Kendi Zanon Takeda**:
  * Configuração inicial e deploy do banco de dados relacional PostgreSQL utilizando infraestrutura de contêineres Docker Compose.
  * Modelagem DDL e criação estrutural das tabelas de auditoria do sistema de gerenciamento de tokens (`password_reset_tokens` e `verification_tokens`).
  * Implementação de seeders de banco de dados para população automática de registros de teste da aplicação.

* **Joao Gabriel Silva Maximiano**:
  * Desenvolvimento da camada de infraestrutura de e-mail utilizando Spring Boot Mail Starter.
  * Implementação da lógica de envio assíncrono de notificações e mensagens de validação de conta (Verification Tokens) após novos cadastros.
  * Escrita e validação de testes de integração básicos para os endpoints públicos expostos da API.

* **Leandro Rodrigues de Melo**:
  * Implementação completa do CRUD mapeado na entidade `FichaTreinoEntity` e seu respectivo ciclo de controle de serviços.
  * Desenvolvimento das tabelas associativas e mapeamento de chaves estrangeiras complexas para o relacionamento de muitos para muitos (`ficha_treino_exercicios`).
  * Tratamento global de exceções na camada de persistência para evitar falhas silenciosas de integridade de banco de dados.

* **Miguel Lemos Ramos**:
  * Modelagem, mapeamento ORM via Hibernate e exposição dos endpoints de controle para as entidades `AulaColetivaEntity` e `AgendamentoEntity`.
  * Criação de regras de validação customizadas para barrar agendamentos duplicados ou que ultrapassassem a capacidade física máxima das salas da academia.
  * Configuração inicial do Springdoc OpenAPI (Swagger UI) para documentação automatizada das rotas REST.

* **Nicolas Kenzo Yanase**:
  * Desenvolvimento da camada de persistência, DTOs e classes controladoras associadas às tabelas de `UnidadeEntity` e `PlanoEntity`.
  * Implementação de validações na camada de entrada da API utilizando regras do Jakarta Bean Validation (ex: `@NotNull`, `@Min`, `@Size`).
  * Organização estrutural das rotas base do ecossistema seguindo o padrão arquitetural MVC/Camadas.

* **Raul dos Santos Araujo**:
  * Configuração fina e granular da camada de segurança global através do Spring Security.
  * Desenvolvimento do fluxo de geração, assinatura e descriptografia de tokens criptográficos JWT.
  * Implementação da entidade de usuários central (`UsuarioEntity`), tratamento dinâmico de níveis hierárquicos de acesso (Roles/Authorities) e validações com `@AuthenticationPrincipal`.
  * Correção e ajuste das restrições de chaves estrangeiras estruturais (`ForeignKey Constraints`) nas tabelas relacionais do Hibernate.
  * Desenvolvimento do microsserviço assíncrono `MqttMetricasListener` para captura e persistência em lote dos dados telemétricos via MQTT/Broker.

        
