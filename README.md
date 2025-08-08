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

## Build do projeto

Após clonar o repositório, execute o seguinte comando para compilar a aplicação e gerar o arquivo .jar:

```
mvn clean package
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

##  Explicações sobre o Case Desenvolvido

A book-api foi desenvolvida como uma API REST em Java utilizando o framework Spring Boot. Seu principal objetivo é disponibilizar endpoints para consulta de livros, com base na **OpenLibrary API pública**. A aplicação permite:

- Listagem de livros por autor, gênero e ID.
- Armazenamento local dos livros consultados no banco PostgreSQL.
- Utilização de cache Redis para melhorar a performance nas requisições mais frequentes.
- Documentação interativa via Swagger.

### Lógica de Negócios

A lógica central segue o fluxo:

1. **Requisição do cliente** → o controller recebe a requisição.
2. **Busca em cache** → o serviço verifica se o dado já está salvo no Redis.
3. **Busca no banco de dados** → caso não esteja em cache, a API busca os dados persistidos no PostgreSQL.
4. **Busca na API externa (OpenLibrary)** → se os dados ainda não forem encontrados, uma requisição é feita à OpenLibrary.
5. **Persistência e cache** → os dados recuperados da API externa são salvos no banco e cacheados no Redis.

Além disso, a lógica de conversão entre entidades (`Book`) e DTOs (`BookDTO`) garante a separação entre o modelo de dados e a camada de apresentação da API.

---

## Melhorias e Considerações Finais

Durante o desenvolvimento, alguns desafios e oportunidades de melhoria foram identificados:

### Melhorias Futuras

- **Autenticação e Autorização**: implementação de segurança via `Spring Security` para controle de acesso.
- **Paginação e Ordenação**: suporte a paginação nos endpoints de listagem para escalabilidade com grandes volumes de dados.
- **Tradução de gêneros**: atualmente os gêneros vêm da API externa em inglês — há espaço para integrar uma camada de tradução automática.
- **Funcionalidade de "visualizados recentemente"**: adicionar rastreamento de livros acessados por usuário.
- **Testes de Integração**: além dos testes unitários, incluir testes de integração com base de dados real e chamadas reais à API externa.

### Desafios Enfrentados

- A OpenLibrary não fornece todos os dados estruturados de forma consistente, o que exigiu tratamento adicional nos serviços de ingestão.
- Traduções de campos como gênero foram tratadas localmente com dicionário estático, por ausência de suporte multilíngue na API original.
- Configuração de cache com Redis exigiu ajustes no serializador para suportar objetos Java.