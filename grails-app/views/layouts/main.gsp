<!doctype html>
<html lang="en" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><g:layoutTitle default="Grails"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>

        <g:layoutHead/>
    </head>
    <body>
        <div id="grailsLogo" role="banner">
            <a href="http://grails.org"><asset:image src="grails_logo.png" alt="Grails"/></a>
            <sec:ifLoggedIn>
                <div style="float: right;  color: black; font-size 1.3em; font-weight: 600; margin: 1.8em 2.5em">
                    Bem Vindo, <sec:loggedInUserInfo field="username"/>
                    <g:link style="margin: 0" controller="logout">
                        <img src="https://cdn1.iconfinder.com/data/icons/CrystalClear/22x22/actions/exit.png" />
                    </g:link>
                </div>
            </sec:ifLoggedIn>
        </div>
        <g:layoutBody/>
        <div class="footer" role="contentinfo"></div>
        <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
    </body>
</html>
