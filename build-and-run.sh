#!/bin/bash

echo "Iniciando build da aplicação book-api..."

# Etapa 0: Garantir que o Maven Wrapper tenha permissão de execução
if [ ! -x "./mvnw" ]; then
  echo "Dando permissão de execução ao Maven Wrapper (./mvnw)..."
  chmod +x mvnw
fi

# Etapa 1: Build do JAR com Maven Wrapper
echo "Gerando JAR com Maven (via Maven Wrapper)..."
./mvnw clean package -DskipTests

# Verifica se o JAR foi gerado com sucesso
if [ ! -f target/book-api-1.0.0.jar ]; then
  echo "Erro: JAR não encontrado em target/"
  exit 1
fi

# Etapa 2: Build da imagem Docker
echo "Build da imagem Docker..."
docker build -t book-api .

# Etapa 3: Subindo containers com Docker Compose
echo "Iniciando containers com Docker Compose..."
docker-compose up