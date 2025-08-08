# book-api

API REST em Java com Spring Boot que consome dados da OpenLibrary, armazena no PostgreSQL e utiliza Redis como cache.

---

## Tecnologias

- Java 17+
- Spring Boot 3.2.5
- PostgreSQL
- Redis
- Docker / Docker Compose
- Swagger/OpenAPI

---

## Clonando o projeto

```
git clone https://github.com/seu-usuario/book-api.git
cd book-api
```

---

## Pré-requisitos

Certifique-se de que você tem as seguintes ferramentas instaladas:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- Java 17+ (caso deseje rodar sem Docker)
- Maven 3.8+ (caso deseje rodar sem Docker)

---

## Executando com Docker

Execute o script abaixo (Linux/macOS/WSL):

```
chmod +x build-and-run.sh
./build-and-run.sh
```

> Se estiver no Windows e ocorrer erro com `chmod`, execute diretamente:
> ```
> ./build-and-run.sh
> ```

Esse script irá:
- Fazer build da aplicação
- Subir containers do PostgreSQL, Redis e da API

---

## Acessando a aplicação

- **Swagger UI**: [http://localhost:8080/book-api/swagger-ui/index.html#/](http://localhost:8080/book-api/swagger-ui.html)
- **API Docs (OpenAPI)**: [http://localhost:8080/book-api/v3/api-docs](http://localhost:8080/book-api/v3/api-docs)

---

## Endpoints disponíveis

| Método | Rota                        | Descrição                         |
|--------|-----------------------------|-----------------------------------|
| GET    | `/books`                    | Lista todos os livros             |
| GET    | `/books/{id}`               | Busca livro por ID                |
| GET    | `/books/author/{author}`    | Busca livros por autor            |
| GET    | `/books/genre/{genre}`      | Busca livros por gênero           |
| DELETE | `/cache/clear`              | Limpa todos os caches (Redis)     |

---

## Rodando os testes

```
mvn test
```

> Os testes utilizam H2 em memória e mocks para Redis/PostgreSQL, não é necessário subir os containers.

---

## Variáveis de ambiente (opcional)

Por padrão, as configurações de banco e Redis estão no `application.properties`, mas você pode sobrescrever via variáveis de ambiente, por exemplo:

```
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/book_db
export SPRING_REDIS_HOST=localhost
```

---

## Considerações finais

- O cache melhora a performance nas buscas repetidas.
- Ao não encontrar um dado no banco, a API busca na OpenLibrary e salva localmente.
- Gêneros vindos da OpenLibrary são traduzidos automaticamente para português.