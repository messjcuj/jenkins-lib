import com.ecom.PipelineHelper
import com.ecom.PipelineGlobalConfig

def agentLabel = PipelineHelper.getSettings(this)["default_agent_label"]

def call(Map parameters = [:]) {
    def globalConfig = parameters.script.globalConfig
    def parallelSteps = [:]
    def settings = PipelineHelper.getSettings(parameters.script)
    def javaHome = settings.jdk[parameters.jdk_version]
    def gradleHome = settings.gradle[parameters.gradle_version]

    if (!javaHome) {
        throw Exception("JDK ${parameters.jdk_version} is not supported")
    }
    if (!gradleHome) {
        throw Exception("Gradle ${parameters.gradle_version} is not supported")
    }

    env.JAVA_HOME = javaHome.
    env.PATH = "${javaHome}/bin:${gradleHome}/bin:${env.PATH}"

    for (repo in globalConfig.repos) {
        parallelSteps[repo] = {
            def command = "gradle clean build ${parameters.global_arguments}"
            buildCommand(repo, command)
        }
    }
    parallel parallelSteps
}

void buildCommand(def repo, def command) {
    println("DEBUG: ${repo}")
    println("DEBUG: ${command}")
    sh "java --version && gradle --version && cd ${env.WORKSPACE}/${repo} &&  ${command}"
}
