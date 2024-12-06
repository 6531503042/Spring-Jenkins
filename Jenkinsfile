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
        
        stage('Test') {
            steps {
                sh './gradlew test'
            }
            post {
                always {
                    // Publish JUnit test results
                    junit '**/build/test-results/test/*.xml'
                    
                    // Publish JaCoCo code coverage
                    jacoco(
                        execPattern: '**/build/jacoco/*.exec',
                        classPattern: '**/build/classes/java/main',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }
        
        stage('Integration Test') {
            steps {
                sh './gradlew integrationTest'
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
        
        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
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
            // Archive test reports
            archiveArtifacts artifacts: '**/build/reports/**'
            cleanWs()
        }
        failure {
            emailext (
                subject: "Build Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                body: "Check console output at ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
    }
} 