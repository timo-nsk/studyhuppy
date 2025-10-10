pipeline {
    agent any

    environment {
        DOCKERHUB_CRED = credentials('dockerhub-credentials')
    }

    stages {
        stage('Deploy: frontend...') {
            when {
                changeset "frontend/**"
            }

            steps {
                dir('frontend') {
                    bat 'npm install'
                    bat 'npm install --save-dev @angular/cli'
                    echo "Building..."
                    bat 'npm run build'
                    echo "Deploying..."
                    bat "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    bat "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-frontend:latest ."
                    bat "docker push ${env.DOCKERHUB_CRED_USR}/studyhuppy-frontend:latest"
                }
            }
        }

        stage('Deploy: modul-service...') {
            when {
                changeset "backend/track/**"
            }

            steps {
                dir('backend/track') {
                    echo "Testing..."
                    bat 'mvn clean package'
                    echo "Deploying..."
                    bat "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    bat "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-modul-service:latest ."
                    bat "docker push ${env.DOCKERHUB_CRED_USR}/studyhuppy-modul-service:latest"
                }
            }
        }

        stage('Deploy: kartei-service...') {
            when {
                changeset "backend/kartei/**"
            }

            steps {
                dir('backend/kartei') {
                    echo "Testing..."
                    bat 'mvn clean package'
                    echo "Deploying..."
                    bat "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    bat "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-kartei-service:latest ."
                    bat "docker push ${env.DOCKERHUB_CRED_USR}/studyhuppy-kartei-service:latest"
                }
            }
        }

        stage('Deploy: mail-service...') {
            when {
                changeset "backend/mail/**"
            }

            steps {
                dir('backend/mail') {
                    echo "Testing..."
                    bat 'mvn clean package'
                    echo "Deploying..."
                    bat "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    bat "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-mail-service:latest ."
                    bat "docker push ${env.DOCKERHUB_CRED_USR}/studyhuppy-mail-service:latest"
                }
            }
        }

        stage('Deploy: authentication-service...') {
            when {
                changeset "backend/authentication/**"
            }

            steps {
                dir('backend/authentication') {
                    echo "Testing..."
                    bat 'mvn clean package'
                    echo "Deploying..."
                    bat "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    bat "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-authentication-service:latest ."
                    bat "docker push ${env.DOCKERHUB_CRED_USR}/studyhuppy-authentication-service:latest"
                }
            }
        }

        stage('Deploy: mindmap-service...') {
            when {
                changeset "backend/mindmap/**"
            }

            steps {
                dir('backend/mindmap') {
                    echo "Testing..."
                    bat 'mvn clean package'
                    echo "Deploying..."
                    bat "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    bat "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-mindmap-service:latest ."
                    bat "docker push ${env.DOCKERHUB_CRED_USR}/studyhuppy-mindmap-service:latest"
                }
            }
        }

        stage('Deploy: actuator-service...') {
            when {
                changeset "backend/actuator/**"
            }

            steps {
                dir('backend/actuator') {
                    echo "Deploying..."
                    bat "docker login --username ${env.DOCKERHUB_CRED_USR} --password ${env.DOCKERHUB_CRED_PSW}"
                    bat "docker build -t ${env.DOCKERHUB_CRED_USR}/studyhuppy-actuator-service:latest ."
                    bat "docker push ${env.DOCKERHUB_CRED_USR}/studyhuppy-actuator-service:latest"
                }
            }
        }
    }
}
