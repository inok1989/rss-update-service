def isRelease = env.TAG_NAME

String version = "${env.BRANCH_NAME}${(isRelease ? ".${env.BUILD_NUMBER}" : '-SNAPSHOT')}"

node {
    try {
        stage('Checkout') {
            cleanWs()
            checkout scm
        }
        stage('Build') {
            echo "My branch is: ${env.BRANCH_NAME}"
            withSonarQubeEnv('localhostSonarQube') {
                bat "gradlew.bat --info clean build bootJar sonarqube -PserverVersion=${version}"
            }
            currentBuild.description = version
        }
        stage('Bundle') {
            fileOperations([fileCopyOperation(excludes: '', flattenFiles: true, includes: 'build/libs/*.jar', targetLocation: 'package')])
            def jarFiles = findFiles(glob: 'package/*.jar')
            fileOperations([fileRenameOperation(destination: 'package/tt-rss-windows-update-service.jar', source: "package/${jarFiles[0].name}")])
            zip archive: true, dir: 'package', glob: '', zipFile: "tt-rss-windows-update-service-${version}.zip"
        }
        stage('Archive') {
            archiveArtifacts artifacts: "tt-rss-windows-update-service-${version}.zip"
        }
    } catch (Throwable t) {
        throw t
    }
}
