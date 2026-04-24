# 📅 Events Management API

Uma API moderna e robusta desenvolvida em **Spring Boot 3.5** para gerenciamento de eventos técnicos. A aplicação permite a criação de eventos com upload de imagens, gerenciamento de cupons de desconto, localização geográfica(para eventos presenciais) e paginação inteligente.

## 🚀 Tecnologias Utilizadas

*   **Java 21**: Uso de Records e sintaxe moderna.
*   **Spring Boot 3.5.13**: Framework base.
*   **Spring Data JPA**: Abstração de persistência.
*   **MySQL 8.0**: Banco de dados relacional.
*   **Flyway**: Controle de versionamento do esquema do banco.
*   **Lombok**: Redução de boilerplate.
*   **SpringDoc OpenAPI (Swagger)**: Documentação interativa.
*   **Jakarta Validation**: Validação de DTOs.
    **Maven**: Gerenciador de dependências e build.

## ✨ Funcionalidades

## ✨ Funcionalidades Principais

*   **Gerenciamento de Eventos**: Criação de eventos (remotos ou presenciais) com suporte a upload de arquivos (multipart).
*   **Sistema de Cupons**: Criação de cupons de desconto vinculados a eventos específicos.
*   **Segurança de Arquivos**: Endpoint seguro para servir imagens com proteção contra ataques de *Path Traversal*.
*   **Robustez**: Tratamento global de exceções e uso de transações (`@Transactional`) para garantir integridade dos dados.
*   **Paginação**: Endpoints de listagem otimizados para grandes volumes de dados.

## 🛠️ Configuração e Execução

### Pré-requisitos
*   JDK 21 ou superior.
*   MySQL 8.0 ou superior.
*   Maven (ou use o `mvnw` incluso).

### Passo 1: Clonar o repositório
```bash
git clone https://github.com/seu-usuario/events-api.git
cd events-api
```

### 1. Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto baseado no `.env.example`:
```properties
SPRING_DATASOURCE_URL=jdbc:sgbd://localhost:port/name_db
SPRING_DATASOURCE_USERNAME=seu_usuario
SPRING_DATASOURCE_PASSWORD=sua_senha
# Diretório para armazenamento de imagens, defina um diretório.
# Se o diretorio definido nao existir, a própria aplicação criará.
app.upload.dir=diretorio
```

### 2. Banco de Dados
O Flyway gerenciará as tabelas automaticamente. Certifique-se de que o database existe no seu MySQL.

### 3. Executar
```bash
./mvnw spring-boot:run
```

## 📖 Documentação (Swagger)
Com a aplicação rodando, acesse a interface do Swagger para testar os endpoints:
🔗 [http://localhost:8080/events](http://localhost:8080/events)

## 📁 Estrutura do Projeto
```text
src/main/java/com/events/api/
├── controller/    # Endpoints REST
├── domain/        # Entidades JPA e DTOs (Records)
├── exceptions/    # Exceções personalizadas
├── infra/         # Configurações globais e Exception Handler
├── repositories/  # Interfaces Spring Data JPA
├── service/       # Regras de negócio e lógica de upload
└── ApiApplication.java
```

---
*Este projeto foi desenvolvido para fins de estudo e demonstração de boas práticas em arquitetura Spring Boot.*
