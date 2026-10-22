pipeline {
    agent any

    stages {
        stage('Project Information') {
            steps {
                bat '"C:\\Apps\\apache-maven-3.9.16\\bin\\mvn.cmd" -version'
                bat '"C:\\Apps\\apache-maven-3.9.16\\bin\\mvn.cmd" help:evaluate -Dexpression=project.groupId -q -DforceStdout'
                bat '"C:\\Apps\\apache-maven-3.9.16\\bin\\mvn.cmd" help:evaluate -Dexpression=project.artifactId -q -DforceStdout'
                bat '"C:\\Apps\\apache-maven-3.9.16\\bin\\mvn.cmd" help:evaluate -Dexpression=project.version -q -DforceStdout'
            }
        }

        stage('Build') {
            steps {
                bat '"C:\\Apps\\apache-maven-3.9.16\\bin\\mvn.cmd" compile'
            }
        }

        stage('Test') {
            steps {
                bat '"C:\\Apps\\apache-maven-3.9.16\\bin\\mvn.cmd" test'
            }
        }

        stage('Package') {
            steps {
                bat '"C:\\Apps\\apache-maven-3.9.16\\bin\\mvn.cmd" package -DskipTests'
            }
        }
    }
}