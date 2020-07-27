run-tests:
	mvn clean test -Ptest

stop:
	- docker stop blog-api
clean: stop
	- docker rm blog-api

build-maven:
	rm libs/ -rf
	mvn clean package
	mvn dependency:copy-dependencies
	FILE_NAME=blog-api\#\#$(shell find target/*.war -type f | grep -Eo '[0-9]+)
	
build: clean build-maven
	docker build --build-arg ENVIRONMENT=dev --build-arg FILE_NAME -t blog-api .
	chmod -R ugo+rw target/

run: clean
	docker run -d -p 8081:8080 -m 256m --memory-swap 512m --env-file ./.env \
		--name=blog-api --network net blog-api

start: stop
	docker start blog-api

bash:
	docker container exec -i -t --user root blog-api bash

logs:
	docker logs blog-api

compose-down:
	docker-compose down -v --remove-orphans

compose-up-dev: compose-down
	docker-compose -f docker-compose.yml --compatibility up -d --build --force-recreate

compose-up-stage: compose-down
	docker-compose -f docker-compose.yml -f docker-compose.stage.yml --compatibility up -d --no-recreate

heroku-maven-deploy:
	mvn clean heroku:deploy-war -Pprod -Dmaven.test.skip=true
	chmod -R ugo+rw target/
heroku-logs:
	heroku logs --app=jb-blog-api
	
bash-docker-database:
	docker container exec -i -t postgres bash
	
	
	
