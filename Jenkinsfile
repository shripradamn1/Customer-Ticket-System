pipeline {
    agent any // Runs the pipeline on any available agent

    environment {
        // Define environment variables for JDK and Node.js
        JAVA_HOME = tool name: 'JDK 17', type: 'jdk'
        NODE_HOME = tool name: 'NodeJS 20', type: 'nodejs'
    }

    stages {
        stage('Checkout') {
            steps {
                // Check out the code from Git repository
                git url: 'https://github.com/shripradamn1/Customer-Ticket-System.git', branch: 'main'
            }
        }

        stage('Build Spring Boot') {
            steps {
                dir('backend') {
                    script {
                        // Build the Spring Boot application using Maven
                        withMaven(maven: 'Maven 3') {
                            sh 'mvn clean package'
                        }
                    }
                }
            }
        }

        stage('Build React') {
            steps {
                dir('frontend') {
                    script {
                        // Install dependencies and build the React application
                        sh 'npm install'
                        sh 'npm run build'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                dir('backend') {
                    script {
                        // Run unit tests for Spring Boot application
                        withMaven(maven: 'Maven 3') {
                            sh 'mvn test'
                        }
                    }
                }
                
                dir('frontend') {
                    script {
                        // Run unit tests for React application
                        sh 'npm test'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Placeholder for deployment commands
                    echo 'Deploy steps here'
                    
                    // Example for Heroku deployment (Uncomment and modify as needed):
                    // dir('backend') {
                    //     sh 'heroku deploy:jar target/*.jar --app your-heroku-app-name'
                    // }
                    // dir('frontend') {
                    //     sh 'heroku static:deploy --app your-heroku-app-name'
                    // }
                }
            }
        }
    }

    post {
        always {
            // Clean up workspace after build
            cleanWs()
        }
        
        success {
            // Actions to take if the build is successful
            echo 'Build and deploy succeeded!'
        }
        
        failure {
            // Actions to take if the build fails
            echo 'Build or deploy failed.'
        }
        
        unstable {
            // Actions to take if the build is unstable
            echo 'Build was unstable.'
        }
    }
}
