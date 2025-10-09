pipeline {
    agent any

    environment {
        DOCKERHUB_CRED = credentials('dockerhub-credentials')
    }

    stages {
        stage('Test & Build: modul-service...') {
            when {
                changeset "backend/track/**"
            }
            steps {
                dir('backend/track') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image and Push to Repo: modul-service') {
            steps {
                dir('backend/track') {
                    sh "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    sh "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-modul-service:latest ."
                    sh "docker push ${env.DOCKERHUB_CRED_USR}/calc:latest"
                }
            }
        }

    }
}
