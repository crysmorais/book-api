# book-api

API REST em Java com Spring Boot que consome dados da OpenLibrary, armazena no PostgreSQL e utiliza Redis como cache.

## Tecnologias
- Java 17+
- Spring Boot 3.2.5
- PostgreSQL
- Redis
- Docker / Docker Compose
- Swagger/OpenAPI

## Clonando o projeto

```
git clone https://github.com/seu-usuario/book-api.git
cd book-api
```

## Pré-requisitos

Certifique-se de que você tem as seguintes ferramentas instaladas:

- **Java 17+**
- **Maven 3.8+**
- **Docker**
- **Docker Compose**

## Build do projeto

Após clonar o repositório, execute o seguinte comando para compilar a aplicação e gerar o arquivo `.jar`:

```
mvn clean package -DskipTests
```

## Executando com Docker Compose

Use o script abaixo para buildar e subir os containers da aplicação, banco de dados e Redis:

```
chmod +x build-and-run.sh
./build-and-run.sh
```

> Se estiver no Windows, execute diretamente (sem `chmod`):

```
./build-and-run.sh
```

Esse script irá:
- Compilar a aplicação
- Subir containers do PostgreSQL, Redis e da API com Docker Compose

## Acessando a aplicação

- **Swagger UI:** [http://localhost:8080/book-api/swagger-ui/index.html#/](http://localhost:8080/book-api/swagger-ui/index.html#/)
- **API Docs (OpenAPI):** [http://localhost:8080/book-api/v3/api-docs](http://localhost:8080/book-api/v3/api-docs)

## Endpoints disponíveis

| Método | Rota | Descrição |
|--------|------|-----------|
| GET    | /books                  | Lista todos os livros |
| GET    | /books/{id}            | Busca livro por ID |
| GET    | /books/author/{author} | Busca livros por autor |
| GET    | /books/genre/{genre}   | Busca livros por gênero |
| DELETE | /cache/clear           | Limpa todos os caches (Redis) |

## Rodando os testes

```
mvn test
```

Os testes utilizam H2 em memória e mocks para Redis/PostgreSQL, não é necessário subir os containers.

## Variáveis de ambiente (opcional)

As configurações padrão estão no `application.properties`, mas é possível sobrescrevê-las via variáveis de ambiente:

```
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/book_db
export SPRING_REDIS_HOST=localhost
```

---

## Explicações sobre o Case Desenvolvido

A `book-api` foi desenvolvida como uma API REST em Java utilizando o framework Spring Boot. Seu principal objetivo é disponibilizar endpoints para consulta de livros, com base na OpenLibrary API pública. A aplicação permite:

- Listagem de livros por autor, gênero e ID.
- Armazenamento local dos livros consultados no banco PostgreSQL.
- Utilização de cache Redis para melhorar a performance nas requisições mais frequentes.
- Documentação interativa via Swagger.

### Lógica de Negócios

A lógica central segue o fluxo:

```
Requisição do cliente → o controller recebe a requisição
→ verifica no Redis
→ verifica no PostgreSQL
→ chama a API externa
→ salva no banco e no cache
```

Além disso, a lógica de conversão entre entidades (`Book`) e DTOs (`BookDTO`) garante a separação entre o modelo de dados e a camada de apresentação da API.

---

## Melhorias e Considerações Finais

### Melhorias Futuras
- **Autenticação e Autorização:** implementação de segurança via Spring Security.
- **Paginação e Ordenação:** suporte a paginação nos endpoints.
- **Tradução de Gêneros:** camada de tradução para os gêneros vindos em inglês.
- **Visualizados Recentemente:** rastrear livros acessados por usuário.
- **Testes de Integração:** com banco e API externa.

### Desafios Enfrentados
- A OpenLibrary não fornece todos os dados estruturados de forma consistente.
- Traduções de campos como gênero foram tratadas com dicionário estático.
- Configuração do cache com Redis exigiu ajustes no serializador para suportar objetos Java.
