def readEnvProp
def readWebCap

node {
    def mvnHome
    stage('Get latest Pulls') { // for display purposes
        // Get some code from a GitHub repository
        git 'https://github.com/mohammedtaher95/SeleniumJAVA_TAF.git'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'MAVEN_HOME'
    }

    stage('Setting up Environment') { // for display purposes
        readEnvProp = readProperties file: 'src/main/resources/properties/ExecutionPlatform.properties'
        readWebCap = readProperties file: 'src/main/resources/properties/WebCapabilities.properties'
        readEnvProp.ENV_TYPE = params.Environment_Type
        readEnvProp.CROSS_BROWSER_MODE = params.Cross_Browser_Mode
        readEnvProp.REMOTE_ENV_URL = params.Remote_URL
        readEnvProp.RUN_ALL_TESTS = params.Run_All_Tests
        readWebCap.EXECUTION_METHOD = params.Execution_Method
        readWebCap.TARGET_BROWSER_NAME = params.Target_Browser_Name

        writeEnv = readEnvProp.collect{"${it.key}=${it.value}"}.join('\n')
        writeFile file: 'src/main/resources/properties/ExecutionPlatform.properties', text: writeEnv

        writeCap = readWebCap.collect{"${it.key}=${it.value}"}.join('\n')
        writeFile file: 'src/main/resources/properties/WebCapabilities.properties', text: writeCap

        if (readEnvProp.ENV_TYPE == "GRID") {
            if (isUnix()) {
                sh "docker-compose up -d"
            } else {
                bat("docker-compose up -d")
            }
        }
    }

    stage('Clean Old Builds') {
        // Run the maven build
        withEnv(["MVN_HOME=$mvnHome"]) {
            if (isUnix()) {
                sh '"$MVN_HOME/bin/mvn" clean'
            } else {
                bat(/"%MVN_HOME%\bin\mvn" clean/)
            }
        }
    }
    stage('Run Tests') {
        // Run the maven build
        withEnv(["MVN_HOME=$mvnHome"]) {
            if (isUnix()) {
                sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean test -X'
            } else {
                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean test -X/)
            }
        }
    }

    stage('Teardown Environment') { // for display purposes
        if (readEnvProp.ENV_TYPE == "GRID") {
            if (isUnix()) {
                sh "docker-compose down"
            } else {
                bat("docker-compose down")
            }
        }

    }
    stage('Results') {
        // testng '**/target/surefire-reports/TEST-*.xml'
        // archiveArtifacts 'target/*.jar'
        allure includeProperties: false, jdk: 'JAVA_HOME', results: [[path: 'target/allure-results']]
        step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])

    }
}
