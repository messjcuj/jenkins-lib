def call(Map parameters = [:]) {
    def script = parameters.script
    def globalConfig = script.globalConfig
    def parallelSteps = [:]

    if (!globalConfig.isSubmodules) {
        for (repo in globalConfig.repos) {
            parallelSteps["Checkout ${repo}"] = {
                checkoutBranch(globalConfig.targetBranch, globalConfig.remoteUrl, repo, globalConfig.repoCredentialId)
            }
        }
        parallel parallelSteps      
    } else {
        checkoutSubModules(globalConfig.targetBranch, "${globalConfig.remoteUrl}/${globalConfig.rootModuleRepo}", globalConfig.repoCredentialId)
    } 
}

def checkoutBranch(def branch, def url, def repoName, def credentialsId) {
    echo "Checkout"
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [
            [$class: 'RelativeTargetDirectory', relativeTargetDir: repoName]
        ],
        userRemoteConfigs: [[
            url: "${url}/${repoName}",
            credentialsId: credentialsId
        ]]
    ])
    println("DEBUG: checkout branch: ${branch}")
    println("DEBUG: checkout url: ${url}")
}

def checkoutSubModules(def branch, def url, def credentialsId) {
    checkout([
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
