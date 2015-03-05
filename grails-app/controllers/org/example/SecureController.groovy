package org.example

import grails.plugin.springsecurity.annotation.Secured

class SecureController {

    @Secured(['ROLE_ADMIN'])
    def index() { render 'Admin access only' }
}
