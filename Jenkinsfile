pipeline {
    agent any // Use any available Jenkins agent

    environment {
        // Define environment variables that are used during the build
        JAVA_HOME = tool name: 'JDK 17', type: 'jdk' // Replace with your JDK installation name
        MAVEN_HOME = tool name: 'Maven 3.9.8', type: 'maven' // Replace with your Maven installation name
        //PATH = "${env.MAVEN_HOME}/bin:${env.PATH}" // Add Maven to the PATH
    }

    tools {
        // Specify the Maven and JDK versions to use
        maven 'Maven 3.9.8' // This should match the Maven installation name in Jenkins
        jdk 'JDK 17' // This should match the JDK installation name in Jenkins
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
