package com.ecom

import groovy.json.JsonSlurper

class PipelineGlobalConfig implements Serializable {

    def targetBranch = ""
    def repos = []
    def remoteUrl = ""
    def repoCredentialId = ""
    def isFeatureBranch = false
    def isSubmodules = false
    def rootModuleRepo = ""
    def submodulesRepos = []
    def defaultAgentLabel = "default-agent"
    def agentLabels = [
        "agent-1",
        "agent-2"
    ]
    def jdkHome = [
        "21": "/opt/java/jdk-21.0.7"
    ]
    def gradleHome = [
        "8": "/opt/gradle/gradle-8.14.3"
    ]

}
