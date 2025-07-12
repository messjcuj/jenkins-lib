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

}
