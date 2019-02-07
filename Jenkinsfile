pipeline {
    agent { label 'jenkins-slave' }
    
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
                git([url: 'https://github.com/jovanibrasil/blog-api.git', branch: 'master', credentialsId: '18a17f19-9870-4bcc-8c7b-75eec38a059a'])
                echo 'Installing dependencies ...'
                sh 'npm install'
                echo 'Building ...'
                sh 'docker build -t blog-api ~/workspace/blog-api'
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
                sh 'docker stop blog-api'
                sh 'docker rm blog-api'                
                sh 'docker run -p 8081:8080 --name=blog-api -d blog-api'
            }
        }

        stage("Remove temporary files"){
            steps {
                echo 'cleaning ...'
                echo 'rm ~/workspace/blog-app ~/workspace/blog-app@tmp -rf'
            }
        }

    }
    
}
