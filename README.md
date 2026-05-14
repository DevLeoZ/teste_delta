# Delta - Full Stack Todo App

Uma aplicação full stack simples para gerenciamento de tarefas (todos) com autenticação de usuários. Desenvolvida como teste técnico utilizando Kotlin + Spring Boot no backend e Astro + React no frontend.

## Tecnologias Utilizadas

### Backend
- **Kotlin** - Linguagem principal
- **Spring Boot** - Framework para API REST
- **Spring Security** - Autenticação básica
- **Spring Data JPA** - Acesso a dados
- **SQLite** - Banco de dados
- **JUnit + MockK** - Testes unitários
- **Gradle** - Gerenciamento de dependências

### Frontend
- **Astro** - Framework para sites estáticos
- **React** - Componentes interativos
- **TypeScript** - Tipagem
- **CSS** - Estilização

## Pré-requisitos

- **Java 21** (ou superior)
- **Node.js 22.12.0** (ou superior)
- **Gradle** (incluído no projeto via wrapper)

## Como Rodar

### Backend
1. Navegue até a raiz do projeto.
2. Execute o comando:
   ```
   ./gradlew bootRun
   ```
3. O backend estará rodando em `http://localhost:8080`.

### Frontend
1. Navegue até a pasta `frontend`:
   ```
   cd frontend
   ```
2. Instale as dependências:
   ```
   npm install
   ```
3. Execute o comando:
   ```
   npm run dev
   ```
4. O frontend estará rodando em `http://localhost:4321`.

## API Endpoints

### Autenticação
- `POST /auth/register` - Cadastrar usuário (body: name, lastName, email, password)
- `POST /auth/login` - Login (body: email, password) → retorna { "token": "<userId>" }

### Tarefas (requer header `Authorization: Bearer <userId>`)
- `POST /tasks` - Criar tarefa (body: title, completed)
- `GET /tasks` - Listar tarefas do usuário
- `PUT /tasks/{id}` - Atualizar tarefa (body: title, completed)
- `DELETE /tasks/{id}` - Deletar tarefa

## Estrutura do Projeto

```
delta/
├── build.gradle.kts          # Configuração Gradle (backend)
├── src/main/kotlin/          # Código fonte backend
│   ├── controller/           # Controllers REST
│   ├── entity/               # Entidades JPA
│   ├── repository/           # Repositórios
│   ├── service/              # Lógica de negócio
│   ├── security/             # Configuração de segurança
│   └── dto/                  # DTOs
├── src/test/kotlin/          # Testes unitários
├── frontend/                 # Projeto frontend
│   ├── src/
│   │   ├── components/       # Componentes React
│   │   ├── pages/            # Páginas Astro
│   │   ├── services/         # Funções API
│   │   └── styles/           # CSS
│   ├── package.json          # Dependências frontend
│   └── astro.config.mjs      # Configuração Astro
└── README.md                 # Este arquivo
```

## Testes

### Backend
Execute os testes unitários:
```
./gradlew test
```
- Foco em services (AuthService, TaskService).
- Usa MockK para mocks.

### Frontend
Não há testes automatizados no frontend (simples para o escopo do teste).

## Funcionalidades

- **Cadastro e Login**: Usuários podem se registrar e fazer login.
- **CRUD de Tarefas**: Criar, listar, editar (toggle completed) e deletar tarefas.
- **Segurança**: Autenticação via header; tarefas privadas por usuário.
- **Interface**: Simples e responsiva, com tema dark.

## Notas

- Banco de dados: SQLite (`delta.db`), criado automaticamente.
- Autenticação: Stateless via userId no header (simplificada para o teste).
- CORS: Configurado para desenvolvimento local.

## Autor

Desenvolvido como teste técnico.