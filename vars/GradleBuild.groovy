import com.ecom.PipelineHelper
import com.ecom.PipelineGlobalConfig

def agentLabel = PipelineHelper.getSettings(this)["default_agent_label"]

def call(Map parameters = [:]) {
    def globalConfig = parameters.script.globalConfig
    def parallelSteps = [:]
    def settings = PipelineHelper.getSettings(parameters.script)
    //def javaHome = settings.jdk_home[parameters.jdk_version]
    //def gradleHome = settings.gradle_home[parameters.gradle_version]

    def javaHome = tool name: 'JDK_21', type: 'jdk'
    def gradleHome = tool name: 'Gradle_8', type: 'gradle'

    if (!javaHome) {
        throw Exception("JDK ${parameters.jdk_version} is not supported")
    }
    if (!gradleHome) {
        throw Exception("Gradle ${parameters.gradle_version} is not supported")
    }

    withEnv([
        "JAVA_HOME=/opt/jen",
        "GRADLE_HOME=tes",
        "PATH=asdsa/bin:asd/bin:${env.PATH}"
    ]) {
        sh 'java -version'
        sh 'gradle --version'
    }

    env.JAVA_HOME = javaHome
    env.PATH = "${javaHome}/bin:${gradleHome}/bin:${env.PATH}"

    for (repo in globalConfig.repos) {
        //parallelSteps[repo] = {
            def command = "gradle clean build ${parameters.global_arguments}"
            buildCommand(repo, command)
        //}
    }
    //parallel parallelSteps
}

void buildCommand(def repo, def command) {
    println("DEBUG: ${repo}")
    println("DEBUG: ${command}")
    sh "java --version && gradle --version && cd ${env.WORKSPACE}/${repo} &&  ${command}"
}
