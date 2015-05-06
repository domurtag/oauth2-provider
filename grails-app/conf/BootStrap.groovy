import org.example.Role
import org.example.User
import org.example.UserRole
import org.example.oauth.Client

class BootStrap {

    def init = { servletContext ->

        def saveArgs= [flush: true, failOnError: true]

        def adminRole = new Role(authority: 'ROLE_ADMIN').save(saveArgs)
        def userRole = new Role(authority: 'ROLE_USER').save(saveArgs)
        def clientRole = new Role(authority: 'ROLE_CLIENT').save(saveArgs)

        // create an OAuth client
        new Client(
                clientId: 'my-client',
                authorizedGrantTypes: ['authorization_code', 'refresh_token', 'implicit', 'password', 'client_credentials'],
                authorities: ['ROLE_CLIENT'],
                scopes: ['read', 'write'],
                redirectUris: ['http://localhost:9090/oauth-client/auth/callback'],
                clientSecret: 'secret'
        ).save(saveArgs)

        // create an admin user
        def adminUser = new User(username: 'admin', password: 'password').save(saveArgs)
        UserRole.create adminUser, adminRole, true

        // create a regular user
        def user = new User(username: 'me', password: 'password').save(saveArgs)
        UserRole.create user, userRole, true
    }

    def destroy = {
    }
}
