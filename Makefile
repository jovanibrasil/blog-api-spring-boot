stop:
	- docker stop blog-api
clean: stop
	- docker rm blog-api
build: clean
	mvn clean package
	docker build -t blog-api .
run: clean
	docker run -d -p 8081:8080 --name=blog-api --network net -m 150M blog-api
start: stop
	docker start blog-api
bash:
	docker container exec -i -t --user root blog-api bash
logs:
	docker logs blog-api
compose-up:
	docker-compose up -d
compose-down:
	docker-compose down -v