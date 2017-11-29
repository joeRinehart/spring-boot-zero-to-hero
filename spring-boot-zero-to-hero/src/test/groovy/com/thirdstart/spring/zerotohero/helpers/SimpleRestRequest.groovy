package com.thirdstart.spring.zerotohero.helpers

class SimpleRestRequest {
    Class type = null
    Map params = null
    String path
    String serviceUrl

    String getUri() {
        String uri = serviceUrl + path

        if (params) {
            params.eachWithIndex { String k, v, Integer i ->
                uri += (i == 0 ? '?' : '&') + k + '={' + k + '}'
            }
        }

        return uri
    }

}
