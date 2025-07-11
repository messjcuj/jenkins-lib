package com.ecom

import groovy.json.JsonSlurper

class PipelineHelper implements Serializable {

    static void getDefaultAgents(def script) {
        def jsonStream = script.libraryResource("settings.json")
        def json = new JsonSlurper().parseText(jsonStream)
        return json["default_agent_label"]
    }

    static void getAgents(def script) {
        def jsonStream = script.libraryResource("settings.json")
        def json = new JsonSlurper().parseText(jsonStream)
        return json["agent_label"]
    }
}
