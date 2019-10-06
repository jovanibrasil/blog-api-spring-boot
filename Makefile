run-tests:
	mvn clean test -Ptest

stop:
	- docker stop blog-api
clean: stop
	- docker rm blog-api

build: clean
	mvn clean package -Pdev
	FILE_NAME=blog-api\#\#$(shell find target/*.war -type f | grep -Eo '[0-9]+)
	docker build --build-arg ENVIRONMENT=dev --build-arg FILE_NAME -t blog-api .
	chmod -R ugo+rw target/

run: clean
	docker run -d -p 8081:8080 -m 256m --memory-swap 512m -e "SPRING_PROFILES_ACTIVE=dev" \
	 --name=blog-api --network net blog-api

start: stop
	docker start blog-api

bash:
	docker container exec -i -t --user root blog-api bash

logs:
	docker logs blog-api

compose-down:
	docker-compose down -v 

compose-up: compose-down
	docker-compose up --no-recreate -d

deploy-production:
	/bin/sh scripts/deploy.sh VAULT_TOKEN=${VAULT_TOKEN} SPRING_PROFILES_ACTIVE=${PROFILE}
