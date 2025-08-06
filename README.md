book-api

### 1. Arquitetura da Solução
A book-api é uma aplicação REST construída com Spring Boot, projetada para consultar livros da OpenLibrary API, armazenar dados localmente em PostgreSQL e otimizar consultas frequentes com Redis.

### Tecnologias Utilizadas
- Java 17 + Spring Boot  
- PostgreSQL (persistência local)  
- Redis (cache de livros)  
- OpenLibrary (fonte pública de dados)  
- Swagger (documentação automática)  
- Docker + Docker Compose  

---

### 2. Execução Rápida com Script Automático
Este projeto possui um script chamado `build-and-run.sh` que automatiza o processo de:

- Build do projeto Java com Maven  
- Criação da imagem Docker da aplicação  
- Inicialização do ambiente com `docker-compose`  

####  Como executar

No terminal, dentro da pasta do projeto, execute:

```
chmod +x build-and-run.sh
./build-and-run.sh
```

A aplicação será inicializada e os seguintes serviços serão criados:

- API book-api rodando na porta `8080`
- PostgreSQL na porta `5432`
- Redis na porta `6379`
- Swagger disponível em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

### Windows: Erros comuns com o script `.sh`

 Erro comum ao tentar rodar `chmod +x` ou `./build-and-run.sh` no PowerShell:
```
chmod : O termo 'chmod' não é reconhecido como nome de cmdlet...
```

####  Soluções:

🔹 **Opção 1: Usar Git Bash (recomendado)**  
- Instale o Git para Windows  
- Clique com o botão direito na pasta do projeto e selecione "Git Bash Here"  
- Rode o script:
  ```
  chmod +x build-and-run.sh
  ./build-and-run.sh
  ```

🔹 **Opção 2: Rodar os comandos manualmente no PowerShell**  
Caso não tenha o Git Bash, execute os comandos do script manualmente no terminal:

```powershell
mvn clean package -DskipTests
docker-compose down
docker-compose up --build
```

---

### 3. Explicação sobre o Case Desenvolvido

A API implementa os seguintes endpoints:

- `GET /books` — lista todos os livros persistidos  
- `GET /books/{id}` — consulta um livro pelo ID  
- `GET /books/author/{author}` — consulta livros por autor  
- `GET /books/genre/{genre}` — consulta livros por gênero  

#### Fluxo de Consulta:
1. A API verifica se os dados estão em cache (Redis).  
2. Se não estiverem, consulta o PostgreSQL.  
3. Se ainda não existirem localmente, chama a OpenLibrary, persiste os dados e atualiza o cache.

---

### 4. Melhorias e Considerações Finais

####  Melhorias Futuras:
- Implementar paginação e ordenação  
- Adicionar autenticação JWT  
- Incluir monitoramento com Prometheus + Grafana

####  Desafios Encontrados:
- Estrutura variável das respostas da OpenLibrary  
- Consistência entre cache e banco de dados
