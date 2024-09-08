pipeline {
    agent any // Use any available Jenkins agent

    environment {
        // Define environment variables
        JAVA_HOME = tool name: 'OpenJDK 17', type: 'jdk' // Ensure this matches the OpenJDK configuration name in Jenkins
        MAVEN_HOME = tool name: 'Maven 3.9.8', type: 'maven' // Ensure this matches the Maven configuration name in Jenkins
       // PATH = "${env.MAVEN_HOME}/bin:${env.PATH}" // Add Maven to the PATH
    }

    tools {
        // Specify the Maven and OpenJDK versions to use
        maven 'Maven 3.9.8' // Ensure this matches the Maven installation name in Jenkins
        jdk 'OpenJDK 17' // Ensure this matches the OpenJDK installation name in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from Git repository
                checkout([$class: 'GitSCM', 
                          userRemoteConfigs: [[url: 'https://github.com/shripradamn1/Customer-Ticket-System.git']]
                ])
            }
        }
        
        stage('Build') {
            steps {
                // Execute build commands
                bat 'echo Building...'
                // Replace with actual build command, for example:
                // bat 'msbuild /p:Configuration=Release'
            }
        }
        
        stage('Test') {
            steps {
                // Execute test commands
                bat 'echo Testing...'
                // Replace with actual test command, for example:
                // bat 'vstest.console.exe TestProject.dll'
            }
        }
        
        stage('Package') {
            steps {
                // Execute packaging commands
                bat 'echo Packaging...'
                // Replace with actual packaging command, for example:
                // bat 'dotnet publish -c Release'
            }
        }
    }
    
    post {
        success {
            // Actions to perform on successful build
            echo 'Build and tests succeeded!'
        }
        failure {
            // Actions to perform on failed build
            echo 'Build or tests failed.'
        }
        always {
            // Actions to perform regardless of success or failure
            echo 'Pipeline finished.'
        }
    }
}
