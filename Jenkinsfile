pipeline {
    agent any

    stages {
        stage('Versuche Deployment von: modul-service...') {
            when {
                changeset "backend/track/**"
            }
            steps {
                echo "Änderung in modul-service, starte Deployment..."
                build job: 'studyhuppy-modul-test'
            }
        }

        stage('Versuche Deployment von: kartei-service...') {
            when {
                changeset "backend/kartei/**"
            }
            steps {
                echo "Änderung in kartei-service, starte Deployment..."
                build job: 'studyhuppy-kartei-test'
            }
        }

        stage('Versuche Deployment von: mail-service...') {
            when {
                changeset "backend/mail/**"
            }
            steps {
                echo "Änderung in mail-service, starte Deployment..."
                build job: 'studyhuppy-mail-test'
            }
        }

        stage('Versuche Deployment von: mindmap-service...') {
            when {
                changeset "backend/mindmap/**"
            }
            steps {
                echo "Änderung in mindmap-service, starte Deployment..."
                build job: 'studyhuppy-mindmap-test'
            }
        }

        stage('Versuche Deployment von: authentication-service...') {
            when {
                changeset "backend/authentication/**"
            }
            steps {
                echo "Änderung in authentication-service, starte Deployment..."
                build job: 'studyhuppy-authentication-test'
            }
        }
    }
}
