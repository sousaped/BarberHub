# 💈 BarberHub API

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/Spring Boot-4.0.5-brightgreen?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/MySQL-8-blue?style=for-the-badge&logo=mysql" />
  <img src="https://img.shields.io/badge/Docker-blue?style=for-the-badge&logo=docker" />
  <img src="https://img.shields.io/badge/Swagger-OpenAPI-green?style=for-the-badge&logo=swagger" />
  <img src="https://img.shields.io/badge/Maven-red?style=for-the-badge&logo=apachemaven" />
</p>

> API REST desenvolvida para gerenciar as operações de uma barbearia, oferecendo suporte completo para o controle de usuários, barbeiros, serviços, agendamentos e avaliações.

---

## 📋 Sobre o Projeto

O **BarberHub** é um projeto pessoal desenvolvido com o objetivo de consolidar, na prática, os conhecimentos adquiridos por meio dos cursos da plataforma **Alura** e da **Pós-Graduação em Arquitetura de Desenvolvimento Java pela FIAP**, com foco na aplicação de boas práticas, padrões de projeto e soluções voltadas ao desenvolvimento backend.

---

## 🚀 Funcionalidades

### 👤 Usuários
- Cadastro de usuários com email e telefone únicos
- Atualização de dados do usuário
- Troca de senha
- Listagem de usuários

### 💈 Barbeiros
- Cadastro de barbeiro vinculado a um usuário existente
- Listagem de barbeiros ativos
- Busca por especialidade
- Atualização de especialidades e status
- Desativação de barbeiro

### ✂️ Serviços
- Cadastro de serviços com nome (enum), preço e duração
- Listagem de serviços disponíveis
- Atualização de preço e duração
- Desativação de serviço

### 📅 Agendamentos
- Criação de agendamento com validações de disponibilidade
- Listagem de agendamentos por barbeiro ou por usuário
- Cancelamento de agendamento
- Conclusão de agendamento

### ⭐ Avaliações
- Criação de avaliação vinculando usuário e barbeiro
- Validação de agendamento concluído antes de avaliar
- Listagem de avaliações por barbeiro
- Cálculo automático da média de avaliações do barbeiro
- Exclusão de avaliação

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas do Spring:

```
Controller  →  Service  →  Repository  →  Database
    ↑              ↑
   DTO           Entity
```

### Padrões utilizados
- **DTO Pattern** — separação entre entidade e contrato da API
- **Repository Pattern** — abstração do acesso ao banco de dados
- **Service Layer** — centralização das regras de negócio
- **RESTful API** — endpoints semânticos com verbos HTTP corretos

---

## 🗂️ Estrutura do Projeto

```
src/
└── main/
    └── java/br/com/barberhub/
        ├── controller/
        │   ├── UserController.java
        │   ├── BarberController.java
        │   ├── ServiceItemController.java
        │   ├── AppointmentController.java
        │   └── ReviewController.java
        ├── service/
        │   ├── UserService.java
        │   ├── BarberService.java
        │   ├── ServiceItemService.java
        │   ├── AppointmentService.java
        │   └── ReviewService.java
        ├── repository/
        │   ├── IUserRepository.java
        │   ├── IBarberRepository.java
        │   ├── IServiceItemRepository.java
        │   ├── IAppointmentRepository.java
        │   └── IReviewRepository.java
        ├── entities/
        │   ├── User.java
        │   ├── Barber.java
        │   ├── ServiceItem.java
        │   ├── Appointment.java
        │   └── Review.java
        ├── dto/
        │   ├── UserDTO.java
        │   ├── UserResponseDTO.java
        │   ├── UserUpdateDTO.java
        │   ├── ChangeMyPasswordDTO.java
        │   ├── BarberRequestDTO.java
        │   ├── BarberResponseDTO.java
        │   ├── BarberUpdateDTO.java
        │   ├── ServiceItemRequestDTO.java
        │   ├── ServiceItemResponseDTO.java
        │   ├── ServiceItemUpdateDTO.java
        │   ├── AppointmentRequestDTO.java
        │   ├── AppointmentResponseDTO.java
        │   ├── ReviewRequestDTO.java
        │   └── ReviewResponseDTO.java
        ├── enums/
        │   ├── Specialty.java
        │   └── AppointmentStatus.java
        └── exceptions/
            ├── NotFoundException.java
            └── BadRequestException.java
```

---

## 🗃️ Diagrama de Entidades

```
User ──────────── Barber ─────────── Review
  │                  │
  └──── Appointment ─┘
             │
         ServiceItem
```

| Entidade | Descrição |
|---|---|
| `User` | Usuário do sistema (cliente ou barbeiro) |
| `Barber` | Perfil de barbeiro vinculado a um usuário |
| `ServiceItem` | Serviço oferecido pela barbearia |
| `Appointment` | Agendamento entre cliente, barbeiro e serviço |
| `Review` | Avaliação do cliente sobre o barbeiro |

---

## 🔗 Endpoints

### 👤 Users — `/v1/user`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/v1/user` | Lista todos os usuários |
| `POST` | `/v1/user` | Cria um novo usuário |
| `PUT` | `/v1/user/{id}` | Atualiza dados do usuário |
| `PATCH` | `/v1/user/{id}/change-password` | Altera a senha |
| `PATCH` | `/v1/user/{id}/disable` | Desativa um usuário |

### 💈 Barbers — `/v1/barber`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/v1/barber` | Lista todos os barbeiros |
| `GET` | `/v1/barber/{id}` | Busca barbeiro por ID |
| `GET` | `/v1/barber/specialty/{specialty}` | Busca por especialidade |
| `POST` | `/v1/barber/create` | Cadastra um novo barbeiro |
| `PUT` | `/v1/barber/{id}` | Atualiza dados do barbeiro |
| `PATCH` | `/v1/barber/{id}/disable` | Desativa um barbeiro |

### ✂️ Service Items — `/v1/serviceitem`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/v1/serviceitem` | Lista todos os serviços |
| `GET` | `/v1/serviceitem/{id}` | Busca serviço por ID |
| `POST` | `/v1/serviceitem/create` | Cadastra um novo serviço |
| `PUT` | `/v1/serviceitem/{id}` | Atualiza um serviço |
| `PATCH` | `/v1/serviceitem/{id}/disable` | Desativa um serviço |

### 📅 Appointments — `/v1/appointment`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/v1/appointment` | Lista todos os agendamentos |
| `GET` | `/v1/appointment/barber/{id}` | Lista agendamentos por barbeiro |
| `GET` | `/v1/appointment/user/{id}` | Lista agendamentos por usuário |
| `POST` | `/v1/appointment/create` | Cria um novo agendamento |
| `PATCH` | `/v1/appointment/{id}/cancel` | Cancela um agendamento |
| `PATCH` | `/v1/appointment/{id}/complete` | Conclui um agendamento |

### ⭐ Reviews — `/v1/review`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/v1/review/barber/{id}` | Lista avaliações de um barbeiro |
| `POST` | `/v1/review/create` | Cria uma avaliação |
| `DELETE` | `/v1/review/{id}` | Remove uma avaliação |

---

## ✅ Regras de Negócio

### Agendamentos
- O barbeiro deve estar **ativo** para receber agendamentos
- Não é permitido agendar em um **horário já ocupado** pelo barbeiro
- O cliente não pode ter **dois agendamentos no mesmo horário**
- Status possíveis: `SCHEDULED`, `COMPLETED`, `CANCELLED`
- Não é possível cancelar ou concluir um agendamento já finalizado

### Avaliações
- O cliente só pode avaliar um barbeiro se tiver um **agendamento concluído** com ele
- A nota deve ser entre **1 e 5**
- A **média de avaliações** do barbeiro é recalculada automaticamente a cada nova avaliação ou exclusão

### Barbeiros
- Um usuário só pode ser vinculado a **um perfil de barbeiro**
- Barbeiros desativados não aparecem disponíveis para agendamento

### Serviços
- Não é permitido cadastrar dois serviços com o **mesmo nome**

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.5 | Framework principal |
| Spring Data JPA | — | Persistência de dados |
| Hibernate | 7.2.7 | ORM |
| MySQL | 8 | Banco de dados |
| Docker | — | Conteinerização |
| Lombok | — | Redução de boilerplate |
| SpringDoc OpenAPI (Swagger) | — | Documentação da API |
| Maven | — | Gerenciamento de dependências |
| Jakarta Validation | — | Validação de dados |

---

## ⚙️ Como Executar

### 🐳 Com Docker (recomendado)

**Pré-requisitos**
- Docker
- Docker Compose

**1. Crie o arquivo `.env` na raiz do projeto:**

```env
DB_HOST=db
DB_NAME=barber_hub
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
MYSQL_ROOT_PASSWORD=sua_senha_root
```

**2. Crie o arquivo `docker-compose.yml` na raiz do projeto:**

```yaml
services:
  db:
    image: mysql:8
    container_name: barberhub-db
    environment:
      MYSQL_DATABASE: ${DB_NAME}
      MYSQL_USER: ${DB_USER}
      MYSQL_PASSWORD: ${DB_PASSWORD}
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  app:
    image: alekey02/barberhub:1.0
    container_name: barberhub-app
    ports:
      - "8080:8080"
    environment:
      DB_HOST: ${DB_HOST}
      DB_NAME: ${DB_NAME}
      DB_USER: ${DB_USER}
      DB_PASSWORD: ${DB_PASSWORD}
    depends_on:
      db:
        condition: service_healthy
```

**3. Suba os containers:**

```bash
docker-compose up
```

---

### 💻 Sem Docker

**Pré-requisitos**
- Java 21+
- Maven
- MySQL 8

**Configuração do banco de dados**

No arquivo `application.properties`, configure as credenciais:

```properties
spring.datasource.url=jdbc:mysql://localhost/barber_hub?createDatabaseIfNotExist=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

**Executando a aplicação**

```bash
# Clone o repositório
git clone https://github.com/sousaped/BarberHub.git

# Entre na pasta do projeto
cd BarberHub

# Execute com Maven
./mvnw spring-boot:run
```

---

A API estará disponível em `http://localhost:8080`

---

## 📖 Documentação

Com a aplicação rodando, acesse a documentação interativa via Swagger:

```
http://localhost:8080/swagger-ui.html
```

---

## 🐳 Docker Hub

A imagem da aplicação está disponível no Docker Hub:

```bash
docker pull alekey02/barberhub:1.1
```

🔗 [alekey02/barberhub](https://hub.docker.com/r/alekey02/barberhub)

---

## 📌 Próximos Passos

- [ ] Implementação do Spring Security com JWT
- [ ] Controle de acesso por roles (CLIENT, BARBER, ADMIN)
- [ ] Testes unitários e de integração

---

## 👨‍💻 Autor

Desenvolvido por **Alex** — projeto pessoal para consolidação de conhecimentos em desenvolvimento backend com Java e Spring Boot.
