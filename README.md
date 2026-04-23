# 📅 Events Management API

Uma API moderna e robusta desenvolvida em **Spring Boot 3.5** para gerenciamento de eventos técnicos. A aplicação permite a criação de eventos com upload de imagens, gerenciamento de cupons de desconto, localização geográfica (para eventos presenciais) e paginação inteligente.

## 🚀 Tecnologias Utilizadas

*   **Java 21**: Aproveitando as últimas funcionalidades da linguagem (como Records).
*   **Spring Boot 3.5.13**: Base do ecossistema.
*   **Spring Data JPA**: Abstração de persistência de dados.
*   **MySQL**: Banco de dados relacional.
*   **Flyway**: Controle de versionamento e migrações do banco de dados.
*   **Lombok**: Redução de código boilerplate (getters, setters, construtores).
*   **Jakarta Bean Validation**: Validação rigorosa dos dados de entrada.
*   **Maven**: Gerenciador de dependências e build.

## ✨ Funcionalidades Principais

*   **Gerenciamento de Eventos**: Criação de eventos (remotos ou presenciais) com suporte a upload de arquivos (multipart).
*   **Sistema de Cupons**: Criação de cupons de desconto vinculados a eventos específicos.
*   **Segurança de Arquivos**: Endpoint seguro para servir imagens com proteção contra ataques de *Path Traversal*.
*   **Robustez**: Tratamento global de exceções e uso de transações (`@Transactional`) para garantir integridade dos dados.
*   **Paginação**: Endpoints de listagem otimizados para grandes volumes de dados.

## 🛠️ Configuração e Instalação

### Pré-requisitos
*   JDK 21 ou superior.
*   MySQL 8.0 ou superior.
*   Maven (ou use o `mvnw` incluso).

### Passo 1: Clonar o repositório
```bash
git clone https://github.com/seu-usuario/events-api.git
cd events-api
```

### Passo 2: Configurar o Banco de Dados
Crie um banco de dados no MySQL chamado `api_events_db` e ajuste as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/api_events_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Diretório para armazenamento de imagens
app.upload.dir=uploads/events/
```

### Passo 3: Executar a aplicação
O Flyway criará as tabelas automaticamente na primeira execução.
```bash
./mvnw spring-boot:run
```

## 📖 Documentação da API (Endpoints)

### Eventos
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/event/upload` | Cria um evento com imagem (form-data). |
| `GET` | `/api/event` | Lista todos os eventos com paginação. |
| `GET` | `/api/event/held` | Lista eventos que já ocorreram. |
| `GET` | `/api/event/{id}` | Busca detalhes de um evento específico. |
| `GET` | `/api/event/image/{filename}` | Recupera uma imagem de forma segura. |

### Cupons
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/coupon/event/{id}` | Cria um cupom para o evento informado. |

## 🛡️ Tratamento de Erros
A API utiliza um Handler Global para retornar erros padronizados:
*   **400 Bad Request**: Dados inválidos ou violação de regra de negócio.
*   **404 Not Found**: Recurso (evento/cupom) não encontrado.
*   **500 Internal Server Error**: Erros inesperados do sistema.

**Exemplo de erro:**
```json
{
  "message": "A data do evento é obrigatória",
  "statusCode": 400
}
```

## 📂 Estrutura de Pastas
```text
src/main/java/com/events/api/
├── controller/    # Camada de entrada (REST)
├── domain/        # Entidades e DTOs
├── exceptions/    # Exceções customizadas
├── infra/         # Configurações e Exception Handler
├── repositories/  # Interface com o Banco de Dados
├── service/       # Regras de negócio
└── ApiApplication.java
```

---
*Este projeto foi desenvolvido para fins de estudo e demonstração de boas práticas em arquitetura Spring Boot.*
