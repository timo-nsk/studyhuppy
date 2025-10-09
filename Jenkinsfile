pipeline {
    agent any

    stages {
        stage('Deploy modul-service...') {
            when { changeset "backend/track/**" }
                steps {
                    build job: 'studyhuppy-modul-test'
            }

            post {
                skipped {
                    echo 'Deployment von modul-service übersprungen, da keine Änderungen.'
                }
            }
        }
    }
}