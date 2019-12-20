ifndef PROFILE
override PROFILE = stage
endif

run-tests:
	mvn clean test -Ptest

stop:
	- docker stop blog-api
clean: stop
	- docker rm blog-api

build-maven:
	rm libs/ -rf
	mvn clean package -Pstage
	mvn dependency:copy-dependencies
	FILE_NAME=blog-api\#\#$(shell find target/*.war -type f | grep -Eo '[0-9]+)

build-docker: clean
	docker build --build-arg ENVIRONMENT=dev --build-arg FILE_NAME -t blog-api .
	chmod -R ugo+rw target/
	
build: clean
	rm libs/ -rf
	mvn clean package -Pstage
	mvn dependency:copy-dependencies
	FILE_NAME=blog-api\#\#$(shell find target/*.war -type f | grep -Eo '[0-9]+)
	docker build --build-arg ENVIRONMENT=stage --build-arg FILE_NAME -t blog-api .
	chmod -R ugo+rw target/

run: clean
	docker run -d -p 8081:8080 -m 256m --memory-swap 512m -e "SPRING_PROFILES_ACTIVE=dev" \
	 -e "VAULT_TOKEN=${VAULT_TOKEN}" --name=blog-api --network net blog-api

start: stop
	docker start blog-api

bash:
	docker container exec -i -t --user root blog-api bash

logs:
	docker logs blog-api

compose-down:
	#docker network disconnect -f blog-api_net blog-api
	docker-compose down -v --remove-orphans

compose-up-dev: compose-down
	docker-compose -f docker-compose.yml --compatibility up -d --no-recreate

compose-up-stage: compose-down
	docker-compose -f docker-compose.yml -f docker-compose.stage.yml --compatibility up -d --no-recreate

deploy-production:
	/bin/sh scripts/deploy-docker-tomcat.sh VAULT_TOKEN=${VAULT_TOKEN} SPRING_PROFILES_ACTIVE=${PROFILE}
