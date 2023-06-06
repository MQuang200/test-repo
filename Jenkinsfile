pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          volumes:
          - name: data
            nfs:
              server: "10.96.200.2"
              path: "/" # "shared" folder must exist inside "/exports" directory of NFS server
          containers:
          - name: java
            image: openjdk:17
            volumeMounts:
                - name: data
                  mountPath: /mnt/data
            command:
            - cat
            tty: true
        '''
    }
  }
  stages {
    stage('Build stage') {
      steps {
        git url: 'https://github.com/quang2652001/test-repo.git', branch: 'main'
        script {
          def id = env.BRANCH_NAME.replace("origin/", "").split("-")[0]
        }
        container('java') {
        //   sh 'ls /mnt/data/artifact'
          sh 'echo "ID ${id}"'
          sh 'pwd'
          sh 'mkdir -p /mnt/data/source-code'
          sh 'cp ./*.java /mnt/data/source-code'
          sh 'mkdir -p /mnt/data/artifact'
          sh 'javac -d /mnt/data/artifact /mnt/data/source-code/*.java'
          sh 'java -jar /mnt/data/analyzer-lca-spring-0.0.1-SNAPSHOT.jar'
          sh 'cp /mnt/data/artifact/Questions.txt .'
          sh 'ls'
          sh 'ls /mnt/data/artifact'
        }
      }
      post {
            always {
                archiveArtifacts 'Questions.txt'
            }
        }
    }
    stage('Run stage') {
      steps {
        container('java') {
          sh 'pwd'
          sh 'cd /mnt/data/artifact && java Lab2/Main 3'
        }
      }
    }
  }
  post {
        failure {
          script {
            def email = env.BRANCH_NAME.replace("origin/", "").split("-")[1]
          }
            emailext (
                to: email,
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: "Check the attached report.",
                attachLog: true,
            )
        }

        success {
            emailext (
                to: email",
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: "Check the attached report.",
                attachLog: false,
                attachmentsPattern: "**.txt"
            )
        }
    }
}
