pipeline {
    agent any
    
    tools {
        jdk 'JDK 17'
    }
    
    environment {
        SONAR_HOST_URL = 'http://sonarqube:9000'
    }
    
    stages {
        stage('Clean') {
            steps {
                sh './gradlew clean'
            }
        }
        
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './gradlew sonarqube'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("modern-crud:${env.BUILD_NUMBER}")
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    docker.image("modern-crud:${env.BUILD_NUMBER}").run('-p 8080:8080')
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
} 