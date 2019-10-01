#!/bin/sh

# Parameters: FILENAME and REVISION

CONTAINER_NAME=apps-server
CATALINA_HOME=/usr/local/tomcat/webapps/
FILENAME=blog-api

if [ "$(systemctl is-active docker)" = "active" ];
	then
		echo "DOCKER ACTIVE"

		COUNT=$(docker ps -a | grep "$CONTAINER_NAME" | wc -l)
		if ($COUNT <= 0 || COUNT="");
			then
				echo "CONTAINER STOPPED OR NOT FOUND"
				echo "RUNNING ${CONTAINER_NAME} ..."
				#docker stop $CONTAINER_NAME
				#docker rm $CONTAINER_NAME
				#docker build --build-arg ENVIRONMENT=dev -t $CONTAINER_NAME .
				docker run -d -p 8090:8080 -m 256m --memory-swap 512m \
					   	-e VAULT_TOKEN=${VAULT_TOKEN} -e SPRING_PROFILES_ACTIVE=${PROFILE} \
					   	--name=$CONTAINER_NAME --network net $CONTAINER_NAME
		fi

		echo "BUILDING APP ..."
		mvn clean package -Pdev
		find target/*.war -type f -execdir cp "{}" ../ ";"
		REVISION=$(find *.war -type f | grep -Eo '[0-9]+')
		echo "APP BUILDING REVISION ${REVISION}"
		chmod -R ugo+rwx target
		chmod -R ugo+rwx src

		echo "CONTAINER RUNNING. DEPLOYING ..."
		docker cp ${FILENAME}\#\#${REVISION}.war apps-server:${CATALINA_HOME}${FILENAME}${REVISION}.war
		mv *.war builds/
		echo "FINISHED!"
	else
		echo "DOCKER INACTIVE"
	fi

