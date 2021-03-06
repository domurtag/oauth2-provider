// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
                      all          : '*/*', // 'all' maps to '*' or the first available format in withFormat
                      atom         : 'application/atom+xml',
                      css          : 'text/css',
                      csv          : 'text/csv',
                      form         : 'application/x-www-form-urlencoded',
                      html         : ['text/html', 'application/xhtml+xml'],
                      js           : 'text/javascript',
                      json         : ['application/json', 'text/json'],
                      multipartForm: 'multipart/form-data',
                      rss          : 'application/rss+xml',
                      text         : 'text/plain',
                      hal          : ['application/hal+json', 'application/hal+xml'],
                      xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password', 'client_secret']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    appenders {
        console name: 'consoleAppender', layout: pattern(conversionPattern: '%d{dd-MM-yyyy HH:mm:ss,SSS} %5p %c{2} - %m%n')
    }

    root {
        // define the root logger's level and appenders, these will be inherited by all other loggers
        error 'consoleAppender'
    }

    // change the default log level for classes in our app to DEBUG
    def packageRoot = 'org.example'

    def kerpNamespaces = [
            packageRoot,
            "grails.app.conf.$packageRoot",
            "grails.app.filters.$packageRoot",
            "grails.app.taglib.$packageRoot",
            "grails.app.services.$packageRoot",
            "grails.app.controllers.$packageRoot",
            "grails.app.domain.$packageRoot"
    ]

    // statements from the app should be logged at DEBUG level
    kerpNamespaces.each { debug it }

    // suppress these harmless errors http://stackoverflow.com/questions/23858953/grails-2-4-and-hibernate4-errors-with-run-app
    fatal 'org.hibernate.tool.hbm2ddl.SchemaExport'
}

grails.plugin.springsecurity.controllerAnnotations.staticRules = [

        '/oauth/authorize.dispatch': ["isFullyAuthenticated() and (request.getMethod().equals('GET') or request.getMethod().equals('POST'))"],
        '/oauth/token.dispatch'    : ["isFullyAuthenticated() and request.getMethod().equals('POST')"],

        // added by Spring Security Core
        '/'                        : ['permitAll'],
        '/index'                   : ['permitAll'],
        '/index.gsp'               : ['permitAll'],
        '/assets/**'               : ['permitAll'],
        '/**/js/**'                : ['permitAll'],
        '/**/css/**'               : ['permitAll'],
        '/**/images/**'            : ['permitAll'],
        '/**/favicon.ico'          : ['permitAll'],
        "/console/**"              : ['permitAll'],
        "/plugins/console*/**"     : ['permitAll']
]

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.example.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.example.UserRole'
grails.plugin.springsecurity.authority.className = 'org.example.Role'

// Added by the Spring Security OAuth2 Provider plugin:
grails.plugin.springsecurity.oauthProvider.clientLookup.className = 'org.example.oauth.Client'
grails.plugin.springsecurity.oauthProvider.authorizationCodeLookup.className = 'org.example.oauth.AuthorizationCode'
grails.plugin.springsecurity.oauthProvider.accessTokenLookup.className = 'org.example.oauth.AccessToken'
grails.plugin.springsecurity.oauthProvider.refreshTokenLookup.className = 'org.example.oauth.RefreshToken'

grails.plugin.springsecurity.providerNames = [
        'clientCredentialsAuthenticationProvider',
        'daoAuthenticationProvider',
        'anonymousAuthenticationProvider',
        'rememberMeAuthenticationProvider'
]

grails.plugin.springsecurity.filterChain.chainMap = [
        '/oauth/token': 'JOINED_FILTERS,-oauth2ProviderFilter,-securityContextPersistenceFilter,-logoutFilter,-rememberMeAuthenticationFilter,-exceptionTranslationFilter',
        '/securedOAuth2Resources/**': 'JOINED_FILTERS,-securityContextPersistenceFilter,-logoutFilter,-rememberMeAuthenticationFilter,-exceptionTranslationFilter',
        '/**': 'JOINED_FILTERS,-statelessSecurityContextPersistenceFilter,-oauth2ProviderFilter,-clientCredentialsTokenEndpointFilter,-oauth2ExceptionTranslationFilter'
]