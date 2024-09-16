pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/backend-code-latest-branch']],
                    userRemoteConfigs: [[url: 'https://github.com/shripradamn1/Customer-Ticket-System.git']]
                ])
            }
        }
        stage('Maven') {
            steps {
                bat '''
                mvn clean install
                '''
            }
        }
        stage('Pull Docker Image') {
            steps {
                bat "docker pull alpine"
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        bat "docker rmi -f csmt || echo 'No image to remove'"
                        bat "docker rm -f csmt || echo 'No container to remove'"
                        echo "Removed existing Docker image and container"
                        bat "docker build -t csmt ."
                    }
                }
            }
        }
    }
}
