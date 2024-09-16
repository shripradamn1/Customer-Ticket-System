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
        stage("Maven") {
            steps {
                bat '''
                mvn install
                '''
            }
        }
        stage("Pull Docker Image") {
            steps {
                bat "docker pull alpine"
            }
        }
        stage("Build Docker Image") {
            steps {
                script {
                    try {
                        bat "docker rmi -f csmt"
                        bat "docker rm -f csmt"
                        echo "Removed existing Docker image and building a new one and deleting the container of mysql"
                    } catch(Exception e) {
                        echo "Exception occurred: " + e.toString()
                    }
                }
            }
        }
        }
    }
}
