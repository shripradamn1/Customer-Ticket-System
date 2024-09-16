pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                echo "Cloning repository..."
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/backend-code-latest-branch']],
                    userRemoteConfigs: [[url: 'https://github.com/shripradamn1/Customer-Ticket-System']]
                ])
            }
        }
        stage('Maven Build') {
            steps {
                echo "Running Maven build..."
                bat 'mvn clean install'
            }
        }
        stage('Pull Docker Image') {
            steps {
                echo "Pulling Docker image..."
                bat "docker pull alpine"
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
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
