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
          env.id = env.BRANCH_NAME.replace("origin/", "").split("-")[0]
          env.email = env.BRANCH_NAME.replace("origin/", "").split("-")[1]
        }
        container('java') {
        //   sh 'ls /mnt/data/artifact'
          sh '''
            parent_dir="/mnt/data"
            prefix="${id}"

            # Find the maximum existing index
            max_index=$(ls -d $parent_dir/$prefix-* 2>/dev/null | sed "s|$parent_dir/$prefix-||" | sort -nr | head -n1)

            # If no existing index, start from 1, otherwise increment the index
            if [ -z "$max_index" ]; then
                new_index=1
            else
                new_index=$((max_index + 1))
                rm -rf "$parent_dir/$prefix-$max_index"
            fi

            # Create new directory
            directory_name="$parent_dir/$prefix-$new_index"
            mkdir "$directory_name"
            cp ./*.java "$directory_name"
          '''
          sh 'cp ./*.java "/mnt/data/$directory_name"'
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
          
            emailext (
                to: env.email,
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: "Check the attached report.",
                attachLog: true,
            )
          }
        }

        success {
          script {
            emailext (
                to: env.email,
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: "Check the attached report.",
                attachLog: false,
                attachmentsPattern: "**.txt"
            )
          }
        }
    }
}
