

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'web.agil.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'web.agil.security.UserRole'
grails.plugin.springsecurity.authority.className = 'web.agil.security.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/console':         ['permitAll'],
	'/**':              ['permitAll'],
	'/error':           ['permitAll'],
	'/index':           ['permitAll'],
	'/index.gsp':       ['permitAll'],
	'/shutdown':        ['permitAll'],
	'/assets/**':       ['permitAll'],
	'/**/js/**':        ['permitAll'],
	'/**/css/**':       ['permitAll'],
	'/**/images/**':    ['permitAll'],
	'/**/favicon.ico':  ['permitAll']
]

