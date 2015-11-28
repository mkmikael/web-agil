<%@ page import="web.agil.enums.StatusBoleto" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'boleto.label', default: 'Boleto')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-boleto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-boleto" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <fieldset class="search">
                <g:form action="index">
                    <legend>Filtros</legend>
                    <div class="fieldcontain">
                        <label for="search_statusBoleto"></label>
                        <g:select name="search_statusBoleto" from="${StatusBoleto.values()}" noSelection="['': 'TODOS']"/>
                    </div>
                    <fieldset>
                        <input type="submit" value="Procurar">
                    </fieldset>
                </g:form>
            </fieldset>
            <br>
            <f:table collection="${boletoList}" />

            <div class="pagination">
                <g:paginate total="${boletoCount ?: 0}" />
            </div>
        </div>
    </body>
</html>