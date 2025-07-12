import com.ecom.PipelineGlobalConfig

def call(Map parameters = [:]) {
    def globalConfig = parameters.script.globalConfig
    def parallelSteps = [:]
    def javaHome = globalConfig.jdkHome[parameters.jdk_version]
    def gradleHome = globalConfig.gradleHome[parameters.gradle_version]

    if (!javaHome) {
        throw Exception("JDK ${parameters.jdk_version} is not supported")
    }
    if (!gradleHome) {
        throw Exception("Gradle ${parameters.gradle_version} is not supported")
    }

    for (repo in globalConfig.repos) {
        parallelSteps["Build ${repo}"] = {
            withEnv([
                "JAVA_HOME=${javaHome}",
                "GRADLE_HOME=${gradleHome}",
                "PATH=${javaHome}/bin:${gradleHome}/bin:${env.PATH}"
            ]) {
                def command = "gradle clean build ${parameters.global_arguments}"
                println("DEBUG: ${repo}")
                println("DEBUG: ${command}")
                sh "java --version && cd ${env.WORKSPACE}/${repo} &&  ${command}"
            }
        }
    }
    parallel parallelSteps
}
