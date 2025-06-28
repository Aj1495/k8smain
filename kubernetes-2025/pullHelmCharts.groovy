def call(String serviceName, String branchName) {
  stage('Pull K8s Manifests') {
    ansiColor('xterm') {
      withCredentials([
        usernamePassword(
          credentialsId: '1360ab06-c1b5-4bc8-bc4d-89977f8400cf',
          usernameVariable: 'GIT_USER',
          passwordVariable: 'GIT_TOKEN'
        )
      ]) {
        try {
          sh """ 
            echo "Preparing to pull Helm/K8s Manifests from repo ..."

            rm -rf k8s-mainfest
            mkdir k8s-mainfest
            cd k8s-mainfest
            git init
            git remote add origin https://\$GIT_USER:\$GIT_TOKEN@github.com/Aj1495/k8smain.git
            git pull origin main
          """
        } catch (Exception e) {
          currentBuild.result = 'FAILURE'
          throw e
        }
      }
    }
  }
}
