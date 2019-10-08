[![Build Status](http://13.58.51.172:8085/buildStatus/icon?job=blog-api)](http://13.58.51.172:8085/job/blog-api/)

# API para um blog pessoal

Esta API permite operações básicas de um blog pessoal. O blog [blog.jovanibrasil.com](https://blog.jovanibrasil.com) (limitações dos recursos de hardware do servidor podem tornar o aplicativo lento ou indisponível) é um exemplo da utilização desta API.   

Operações sobre posts como criar, atualizar, deletar, e buscar
estão disponíveis nesta API. As operações que realizam alteração do estado do sistema em relação aos posts 
são realizadas mediante verificação de autenticação e autorização. 

## Executando

É possível subir a API em um servidor Tomcat em um container Docker. Através de comandos que estão no 
arquivo Makefile podemos executar tarefas básicas para controle deste container. Dentre os comandos 
existentes no arquivo temos comandos para rodar o docker-compose, onde temos o ambiente completo para
rodar a API, e também para rodar a API isoladamente.

Para rodar o docker-compose é necessário que já tenha sido feito build da imagem da API de autorização, 
e da imagem do MYSQL. 

## Documentação

Em breve será apresentada a documentação gerada com Swagger.

## Licença

Em breve será definido qual tipo de licença será utilizada. 


