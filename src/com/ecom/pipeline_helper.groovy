package com.ecom

class PipelineHelper implements Serializable {

    static void getDefaultAgents() {
        def jsonText = script.readFile(jsonFilePath)
        def json = new JsonSlurper().parseText(jsonText)
        return json.default_agent_label
    }

    static void getAgents() {
        def jsonText = script.readFile(jsonFilePath)
        def json = new JsonSlurper().parseText(jsonText)
        return json.agent_label
    }
}
