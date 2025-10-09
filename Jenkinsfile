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
    }
}
