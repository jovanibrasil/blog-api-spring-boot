FROM tomcat:9.0.37-jdk11-openjdk
LABEL maintainer="jovanibrasil@gmail.com"
USER root
RUN apt-get -y update && apt-get -y install netcat

ARG BLOG_DB_URL 
ARG BLOG_DB_USERNAME 
ARG BLOG_DB_PASSWORD
ARG ENVIRONMENT
ARG VERSION

ENV BLOG_DB_URL=$BLOG_DB_URL
ENV BLOG_DB_USERNAME=$BLOG_DB_USERNAME
ENV BLOG_DB_PASSWORD=$BLOG_DB_PASSWORD
ENV ENVIRONMENT=$ENVIRONMENT

RUN mkdir -p /apps/blog/

COPY ./target/blog-api##${VERSION}.war /usr/local/tomcat/webapps/ROOT##${VERSION}.war
COPY ./target/blog-api##${VERSION} /usr/local/tomcat/webapps/ROOT##${VERSION}

COPY ./scripts ./scripts
RUN if [ "$ENVIRONMENT" = "dev" ]; \
	   	then cp ./scripts/startup-dev.sh /startup.sh; \
   		else cp ./scripts/startup-prod.sh /startup.sh;\
  	fi
RUN rm ./scripts -rf
EXPOSE 8080

RUN groupadd -g 1024 datag
RUN adduser --disabled-password --gecos "" --force-badname --ingroup datag myuser 
USER myuser

CMD ["/bin/bash", "/startup.sh"]
