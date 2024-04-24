pipeline {
    agent any

    tools {
        maven 'M3'
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                sh 'cp target/investor-app-0.0.1-SNAPSHOT.jar /home/autocars/api.dailycar.ru/investor-app/'
                sh 'sudo systemctl restart investor-app'
                sh 'sudo systemctl restart api-gateway.service'
            }
        }
    }
}