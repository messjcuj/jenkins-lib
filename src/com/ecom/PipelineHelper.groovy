package com.ecom

import groovy.json.JsonSlurper

class PipelineHelper {

    static void getSettings(def script) {
        def jsonStream = script.libraryResource("settings.json")
        return new JsonSlurper().parseText(jsonStream) 
    }
}
