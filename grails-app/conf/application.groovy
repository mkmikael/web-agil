

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'web.agil.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'web.agil.security.UserRole'
grails.plugin.springsecurity.authority.className = 'web.agil.security.Role'
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.securityConfigType = "Annotation"
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/console':         ["ROLE_MASTER"],
	'/**':  			["ROLE_MASTER"],
	'/error':           ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/shutdown':        ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/assets/**':       ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/**/js/**':        ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/**/css/**':       ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/**/images/**':    ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/**/favicon.ico':  ['IS_AUTHENTICATED_ANONYMOUSLY']
]

