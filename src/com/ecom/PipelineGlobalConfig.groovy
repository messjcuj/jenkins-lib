package com.ecom

import groovy.json.JsonSlurper

class PipelineGlobalConfig implements Serializable {

    String targetBranch = ""
    List<String> repos = []
    String remoteUrl = "",
    String repoCredentialId = ""
    boolean isFeatureBranch = false
    boolean isSubmodules = false
    String rootModuleRepo = ""
    List<String> submodulesRepos = []

}
