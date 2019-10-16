FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root
RUN apt-get -y update && apt-get -y install netcat

ARG BLOG_MYSQL_URL 
ARG BLOG_MYSQL_USERNAME 
ARG BLOG_MYSQL_PASSWORD
ARG ENVIRONMENT
ARG FILE_NAME

ENV BLOG_MYSQL_URL=$BLOG_MYSQL_URL
ENV BLOG_MYSQL_USERNAME=$BLOG_MYSQL_USERNAME
ENV BLOG_MYSQL_PASSWORD=$BLOG_MYSQL_PASSWORD
ENV ENVIRONMENT=$ENVIRONMENT
ENV FILE_NAME=${FILE_NAME}

#COPY ./libs/* /usr/local/tomcat/lib/

COPY ./target/${FILE_NAME} /usr/local/tomcat/webapps/${FILE_NAME}
COPY ./scripts ./scripts
RUN if [ "$ENVIRONMENT" = "dev" ]; \
	   	then cp ./scripts/startup-dev.sh /startup.sh; \
   		else cp ./scripts/startup-prod.sh /startup.sh;\
  	fi
RUN rm ./scripts -rf
EXPOSE 8080

CMD ["/bin/bash", "/startup.sh"]
