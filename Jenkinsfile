pipeline {
    agent any
    // environment {
    //     PATH = "C:/Users/e039326/Downloads/Softwares/apache-maven-3.9.8-bin/apache-maven-3.9.8/bin;${env.PATH}"
    // }
    stages {
        stage('1. Clone from github') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/backend-code-latest-branch']],
                    userRemoteConfigs: [[url: 'https://github.com/shripradamn1/Customer-Ticket-System.git']]
                ])
            }
        }
        stage('2. List Files') {
            steps {
                bat "dir"
            }
        }
        stage("3. Maven clean compile") {
            steps {
                bat "mvn clean compile"
            }
        }
        stage("4. Install the jar") {
            steps {
                bat "mvn install"
            }
        }
        stage("5. Run the tests") {
            steps {
                bat "mvn test"
            }
        }
        stage("6. Build the docker image") {
            steps {
                bat "docker build -t telcoservice ."
            }
        }
        stage("7. Run the docker compose") {
            steps {
                bat "docker compose up -d"
            }
        }

    }
}