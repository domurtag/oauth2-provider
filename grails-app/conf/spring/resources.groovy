import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.oauth2.provider.token.DefaultTokenServices

// Place your Spring DSL code here
beans = {

    // this method of loading the security config was copied from loadSecurityConfig() in SpringSecurityOauth2ProviderGrailsPlugin.groovy
    def conf = {
        def conf = SpringSecurityUtils.securityConfig
        if (!conf || !conf.active) {
            return null
        }

        SpringSecurityUtils.loadSecondaryConfig 'DefaultOAuth2ProviderSecurityConfig'
        conf = SpringSecurityUtils.securityConfig

        if (!conf.oauthProvider.active) {
            return null
        }
        return conf
    }()


    tokenServices(DefaultTokenServices) {
        // an anonymous (inner) bean that adds the username to the access token response
        tokenEnhancer = { org.example.UsernameTokenEnhancer enhancer -> }

        // These dependencies are the same as those defined by the plugin in SpringSecurityOauth2ProviderGrailsPlugin.groovy
        tokenStore = ref("tokenStore")
        clientDetailsService = ref("clientDetailsService")
        accessTokenValiditySeconds = conf.oauthProvider.tokenServices.accessTokenValiditySeconds
        refreshTokenValiditySeconds = conf.oauthProvider.tokenServices.refreshTokenValiditySeconds
        reuseRefreshToken = conf.oauthProvider.tokenServices.reuseRefreshToken
        supportRefreshToken = conf.oauthProvider.tokenServices.supportRefreshToken
    }
}
