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
            image: openjdk:11
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
    stage('Run java') {
      steps {
        container('java') {
          sh 'java -version'
          sh 'javac -version'
          sh 'ls /mnt/data'
          sh "echo 'hello from java1' > /mnt/data/hello.txt"
        }
      }
    }
    stage('Run node') {
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
          - name: node
            image: node:16-alpine3.12
            volumeMounts:
                - name: data
                  mountPath: /mnt/data
            command:
            - cat
            tty: true
        '''
    }
  }
      steps {
        container('node') {
          sh 'npm version'
          sh 'ls /mnt/data'
          sh 'cat /mnt/data/hello.txt'
        }
      }
    }
  }
}
