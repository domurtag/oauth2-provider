package org.example

import groovy.util.logging.Log4j
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

@Log4j
class UsernameTokenEnhancer implements TokenEnhancer {

    @Override
    OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        if (accessToken) {
            DefaultOAuth2AccessToken oauthAccessToken = accessToken as DefaultOAuth2AccessToken

            oauthAccessToken.additionalInformation = [username: authentication.name]
            accessToken = oauthAccessToken
            log.info "Additional access token info: $accessToken.additionalInformation"
        }

        accessToken
    }
}