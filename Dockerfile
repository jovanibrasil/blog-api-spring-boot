FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root
    #COPY dist/ /app

	ARG BLOG_MYSQL_URL 
    ARG BLOG_MYSQL_USERNAME 
    ARG BLOG_MYSQL_PASSWORD

	ENV BLOG_MYSQL_URL=$BLOG_MYSQL_URL
	ENV BLOG_MYSQL_USERNAME=$BLOG_MYSQL_USERNAME
	ENV BLOG_MYSQL_PASSWORD=$BLOG_MYSQL_PASSWORD

    COPY ./target/blog-api.war /usr/local/tomcat/webapps/blog-api.war
    #COPY ./target/blog-api /usr/local/tomcat/webapps/blog-api
    EXPOSE 8081

#USER jenkins
