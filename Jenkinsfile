pipeline {
    agent any // Use any available Jenkins agent

    environment {
        // Define environment variables
        JAVA_HOME = tool name: 'OpenJDK 17', type: 'jdk' // Ensure this matches the OpenJDK configuration name in Jenkins
        MAVEN_HOME = tool name: 'Maven 3.9.8', type: 'maven' // Ensure this matches the Maven configuration name in Jenkins
        PATH = "${env.MAVEN_HOME}/bin:${env.PATH}" // Add Maven to the PATH
    }

    tools {
        // Specify the Maven and OpenJDK versions to use
        maven 'Maven 3.9.8' // Ensure this matches the Maven installation name in Jenkins
        jdk 'OpenJDK 17' // Ensure this matches the OpenJDK installation name in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your SCM (e.g., Git)
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Run Maven build command using Maven Wrapper
                sh './mvnw clean package' // Use the Maven Wrapper if available; otherwise use 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                // Run Maven test command using Maven Wrapper
                sh './mvnw test' // Use the Maven Wrapper if available; otherwise use 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Optionally, run the packaging command if needed
                sh './mvnw package' // If you don't have Maven Wrapper, use 'mvn package'
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
