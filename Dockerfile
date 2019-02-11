FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root
    #COPY dist/ /app

    COPY ./target/blog-api.war /usr/local/tomcat/webapps/blog-api.war
    #COPY ./target/blog-api /usr/local/tomcat/webapps/blog-api
    EXPOSE 8081

#USER jenkins
