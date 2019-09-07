pipeline {
    agent { label 'aws-agent' }
    
    environment {
        BLOG_MYSQL_URL = credentials('BLOG_MYSQL_URL')
        BLOG_MYSQL_CREDENTIALS = credentials('BLOG_MYSQL_CREDENTIALS')
        BLOG_MYSQL_USERNAME = "${env.BLOG_MYSQL_CREDENTIALS_USR}"
        BLOG_MYSQL_PASSWORD = "${env.BLOG_MYSQL_CREDENTIALS_PSW}"
    }
    
    stages {
 
        stage("Environment configuration") {
            steps {
                sh 'git --version'
                echo "Branch: ${env.BRANCH_NAME}"
                sh 'docker -v'
                sh 'printenv'
            }
        }

        stage("Build") {
            steps {
                echo 'Cloning git ...'
                git([url: 'https://github.com/jovanibrasil/blog-api.git', branch: 'master', credentialsId: '9bae9c61-0a29-483c-a07f-47273c351555'])
                echo 'Installing dependencies ...'
                sh 'mvn clean package'
                echo 'Building ...'
                sh 'docker build --build-arg ENVIRONMENT=prod --build-arg BLOG_MYSQL_URL --build-arg BLOG_MYSQL_USERNAME --build-arg BLOG_MYSQL_PASSWORD -t blog-api ~/workspace/blog-api'
            }
        }

        stage("Test"){
            steps {
                echo 'Todo'
            }
        }

        stage("Registry image"){
            steps {
                echo 'TODO'
            }
        }

        stage("Deploy"){
            steps {
                // sh 'docker stop blog-api'
                // sh 'docker rm blog-api'                
                sh 'make clean'
				sh 'docker run -p 8081:8080 -m 512m --memory-swap 512m -e SPRING_PROFILES_ACTIVE=prod --name=blog-api -d blog-api'
            }
        }

        stage("Remove temporary files"){
            steps {
                echo 'cleaning ...'
                //sh 'rm ~/workspace/blog-api ~/workspace/blog-api@tmp -rf'
            }
        }

    }
    
}
