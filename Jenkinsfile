String version = env.BRANCH_NAME + "." + env.BUILD_NUMBER

node {
    try {
        stage('Checkout') {
            cleanWs()
            checkout scm
        }
        stage('Build') {
            echo "My branch is: ${env.BRANCH_NAME}"
            dir('app') {
                withSonarQubeEnv('localhostSonarQube') {
                    bat "gradlew.bat --info clean build bootJar sonarqube -PserverVersion=${version}"
                }
                currentBuild.description = version
            }
        }
        stage('Bundle') {
            fileOperations([fileCopyOperation(excludes: '', flattenFiles: true, includes: 'build/libs/*.jar', targetLocation: 'package')])
            def jarFiles = findFiles(glob: 'package/*.jar')
            fileOperations([fileRenameOperation(destination: 'package/rss-update-service.jar', source: "package/${jarFiles[0].name}")])
            zip archive: true, dir: 'package', glob: '', zipFile: "rss-update-service-${version}.zip"
        }
        stage('Archive') {
            archiveArtifacts artifacts: 'package/*.zip'
        }
    } catch (Throwable t) {
        throw t
    }
}
