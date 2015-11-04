

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'web.agil.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'web.agil.security.UserRole'
grails.plugin.springsecurity.authority.className = 'web.agil.security.Role'
grails.plugin.springsecurity.logout.postOnly = false
//grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
   '/error':           ['permitAll'],
   '/shutdown':        ['permitAll'],
   '/assets/**':       ['permitAll'],
   '/**/js/**':        ['permitAll'],
   '/**/css/**':       ['permitAll'],
   '/**/images/**':    ['permitAll'],
   '/**/favicon.ico':  ['permitAll'],
   '/login':           ['permitAll'],
   '/login/**':        ['permitAll'],
   '/logout':          ['permitAll'],
   '/logout/**':       ['permitAll'],
   '/**':              ['ROLE_MASTER'],
   '/console/**':      ['ROLE_MASTER'],
   '/index':           ['ROLE_MASTER'],
   '/index.gsp':       ['ROLE_MASTER'],
   '/cliente/**':      ['ROLE_MASTER'],
   '/cobranca/**':     ['ROLE_MASTER'],
   '/fornecedor/**':   ['ROLE_MASTER'],
   '/grupo/**':        ['ROLE_MASTER'],
   '/lote/**':         ['ROLE_MASTER'],
   '/movimento/**':    ['ROLE_MASTER'],
   '/organizacao/**':  ['ROLE_MASTER'],
   '/papel/**':        ['ROLE_MASTER'],
   '/participante/**': ['ROLE_MASTER'],
   '/pedido/**':       ['ROLE_MASTER'],
   '/pessoa/**':       ['ROLE_MASTER'],
   '/prazo/**':        ['ROLE_MASTER'],
   '/produto/**':      ['ROLE_MASTER'],
   '/role/**':         ['ROLE_MASTER'],
   '/tributo/**':      ['ROLE_MASTER'],
   '/user/**':         ['ROLE_MASTER'],
   '/userRole/**':     ['ROLE_MASTER'],
   '/vendedor/**':     ['ROLE_MASTER']
]

