stop:
	- docker stop blog-api
clean: stop
	- docker rm blog-api
build: clean
	mvn clean package -Pdev
	docker build -t blog-api .
	chmod -R ugo+rw target/
run: clean
	docker run -d -p 8081:8080 -e "SPRING_PROFILES_ACTIVE=dev" --name=blog-api --network net blog-api
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
