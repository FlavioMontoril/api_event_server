# 📅 Events Management API

Uma API moderna e robusta desenvolvida em **Spring Boot 3.5** para gerenciamento de eventos técnicos. A aplicação permite a criação de eventos com upload de imagens, gerenciamento de cupons de desconto, localização geográfica (para eventos presenciais) e paginação inteligente.

## 🚀 Tecnologias Utilizadas

*   **Java 21**: Uso de Records e sintaxe moderna.
*   **Spring Boot 3.5.13**: Framework base.
*   **Docker & Docker Compose**: Conteinerização e orquestração.
*   **MySQL 8.0**: Banco de dados relacional.
*   **Flyway**: Controle de versionamento do esquema do banco.
*   **Spring Data JPA**: Abstração de persistência.
*   **SpringDoc OpenAPI (Swagger)**: Documentação interativa.
*   **Lombok**: Redução de boilerplate.

## ✨ Funcionalidades Principais

*   **Gerenciamento de Eventos**: Criação de eventos (remotos ou presenciais) com suporte a upload de arquivos (multipart).
*   **Sistema de Cupons**: Criação de cupons de desconto vinculados a eventos específicos.
*   **Segurança de Arquivos**: Endpoint seguro para servir imagens com proteção contra *Path Traversal*.
*   **Paginação**: Endpoints de listagem otimizados para grandes volumes de dados.
*   **Documentação Automática**: Interface Swagger completa para testes.

## 🛠️ Configuração e Execução

### Pré-requisitos
*   **Docker** e **Docker Compose** (Recomendado)
*   *Ou* JDK 21, Maven e MySQL 8.0 instalados localmente.

### 1. Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto:

```properties
# Banco de Dados (Configurações do Container)
MYSQL_DATABASE=name_db
MYSQL_ROOT_PASSWORD=root
MYSQL_USER=user
MYSQL_PASSWORD=password

# Configurações da API
SPRING_DATASOURCE_DOCKER_URL=jdbc:mysql://db:port/name_db?createDatabaseIfNotExist=true
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=password
APP_UPLOAD_DIR=/diretorio
```

### 2. Executando com Docker (Recomendado)
A aplicação subirá a API e o Banco de Dados automaticamente, realizando o build do projeto dentro do container.

```bash
docker compose up -d --build
```

### 3. Executando Localmente (Manual)
Se preferir rodar sem Docker:
1. Certifique-se de que o MySQL está rodando e o banco existe.
2. Ajuste o `SPRING_DATASOURCE_URL` no `.env` para `localhost`.
3. Execute o comando:
```bash
./mvnw spring-boot:run
```

## 📖 Documentação (Swagger)
Com a aplicação rodando, acesse a interface interativa para testar os endpoints:
🔗 [http://localhost:8080/events](http://localhost:8080/events)

## 📁 Estrutura do Projeto
```text
.
├── src/main/java/com/events/api/
│   ├── controller/    # Endpoints REST
│   ├── domain/        # Entidades JPA e DTOs (Records)
│   ├── infra/         # Configurações globais e Exception Handler
│   ├── repositories/  # Interfaces Spring Data JPA
│   ├── service/       # Regras de negócio e lógica de upload
│   └── ApiApplication.java
├── Dockerfile         # Configuração de build multi-stage
├── docker-compose.yml # Orquestração de containers
└── .dockerignore      # Arquivos ignorados no build Docker
```

---
*Este projeto foi desenvolvido para fins de estudo e demonstração de boas práticas em arquitetura Spring Boot.*
