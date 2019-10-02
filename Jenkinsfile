pipeline {
    agent { label 'aws-agent' }
    
    environment {
        VAULT_TOKEN = credentials('VAULT_TOKEN')
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
            }
        }

        stage("Test"){
            steps {
            	//echo 'Installing dependencies ...'
                //sh 'mvn clean package'
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
                sh 'make deploy-production VAULT_TOKEN=${VAULT_TOKEN} PROFILE=prod'
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
