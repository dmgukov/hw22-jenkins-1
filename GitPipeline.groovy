pipeline {
  agent {
    label "dind"
  }
  
  parameters {
    string(
      name: "branch_name",
      description: "Branch name to checkout",
      defaultValue: "master"
    )
    string(
      name: "image_tag",
      description: "Docker image tag",
      defaultValue: "latest"
    )
  }

  stages {
    stage("Checkout") {
      steps {
        git(
            url: "git@github.com:dmytro-gukov/react-redux-realworld-example-app.git",
            branch: params.branch_name,
            credentialsId: "git-ssh-key",
        )
      }
    }

    stage("Build docker image") {
      steps {
        script {
          docker.build "react-app:${params.image_tag}"
        }
      }
    }
  }
}
