[![Build Status](https://travis-ci.org/jovanibrasil/blog-api.svg?branch=develop)](https://travis-ci.org/jovanibrasil/blog-api)
![Codecov branch](https://img.shields.io/codecov/c/github/jovanibrasil/blog-api/develop)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.blog%3Ablog-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.blog%3Ablog-api)

# API para um blog pessoal

Esta API permite operações básicas de um blog. 

Para rodar este projeto primeiramente você deve subir o ambiente executando ```make compose-up-dev``` (roda um docker-compose com um postgres e um json-server-mock com a dependência da API de autenticação). Então basta executar ```mvn spring-boot:run``` (roda a aplicação localmente) ou ```make build && make run``` (roda a aplicação em um container).
