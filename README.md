[![Build Status](https://travis-ci.org/jovanibrasil/blog-api.svg?branch=develop)](https://travis-ci.org/jovanibrasil/blog-api)
![Codecov branch](https://img.shields.io/codecov/c/github/jovanibrasil/blog-api/develop)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.blog%3Ablog-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.blog%3Ablog-api)

# API para um blog pessoal

Esta API permite operações básicas de um blog. 

Para rodar o projeto você deve primeiro subir o Mysql. Para tanto basta ir ao diretório /mysql no projeto e executar o comando make start. Então basta executa o comando mvn -pl web spring-boot:run na raíz do projeto e a aplicação deve iniciar. Uma vez rodando é possível acessar a documentação Swagger em http://localhost:8081/api/swagger-ui.html.
