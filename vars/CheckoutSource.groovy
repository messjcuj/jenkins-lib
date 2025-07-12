import groovy.json.JsonSlurper
import com.ecom.PipelineHelper

def call(Map parameters = [:]) {
    script = parameters.script
    globalConfig = script.globalConfig
    
    if (!globalConfig.isSubmodules) {
        for (repo in globalConfig.repos) {
            parallel(
                checkoutBranch(script, globalConfig.targetBranch, "${globalConfig.remoteUrl}/${repo}", globalConfig.repoCredentialId)
            )
        }      
    } else {
        checkoutSubModules(script, globalConfig.targetBranch, "${globalConfig.remoteUrl}/${globalConfig.rootModuleRepo}", globalConfig.repoCredentialId)
    } 
}

def checkoutBranch(def script, def branch, def url, def credentialsId) {
    script.checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        userRemoteConfigs: [[
            url: "${url}",
            credentialsId: "${credentialsId}"
        ]]
    ])
    println("DEBUG: checkout branch: ${branch}")
    println("DEBUG: checkout url: ${url}")
}

def checkoutSubModules(def script, def branch, def url, def credentialsId) {
    script.checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[
            url: url,
            credentialsId: credentialsId
        ]],
        doGenerateSubmoduleConfigurations: false,
        submoduleCfg: [],
        extensions: [[$class: 'SubmoduleOption',
            recursiveSubmodules: true,
            trackingSubmodules: false,
            reference: '',
            timeout: 10
        ]]
    ])
    println("DEBUG: checkout branch: ${branch}")
    println("DEBUG: checkout url: ${url}")
}