def call(String credId, String localImage, String repoName, String imageTag = "latest") {
    withCredentials([usernamePassword(
        credentialsId: credId,
        passwordVariable: 'dockerHubPass',
        usernameVariable: 'dockerHubUser'
    )]) {
        sh '''
            set +x
            echo "$dockerHubPass" | docker login \
                --username "$dockerHubUser" \
                --password-stdin
        '''

        sh """
            docker image tag ${localImage}:${imageTag} \
                "\$dockerHubUser/${repoName}:${imageTag}"

            docker push "\$dockerHubUser/${repoName}:${imageTag}"
        """

        sh 'docker logout'
    }
}
