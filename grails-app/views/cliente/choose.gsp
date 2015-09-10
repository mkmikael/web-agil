<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <asset:javascript src="owner/cliente-choose.js" />
    </head>
    <body>
        <a href="#create-cliente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div class="content" role="main">
            <h1>Tipo do Cliente</h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.cliente}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.cliente}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="create" method="post">
                <fieldset class="form">
                    <div class="fieldcontain">
                        <label for="fisica"><input id="fisica" name="choose" value="PF" type="radio" />Pessoa Física </label>
                    </div>
                    <div class="fieldcontain">
                        <label for="cpf">CPF</label>
                        <g:field name="cpf"/>
                    </div>

                    <div class="fieldcontain">
                        <label for="juridica"><input id="juridica" name="choose" value="PJ" type="radio"/>Pessoa Jurídica </label>
                    </div>
                    <div class="fieldcontain">                
                        <label for="cnpj">CNPJ</label>
                        <g:field name="cnpj"/>
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <input type="submit" value="Próximo"/>
                </fieldset>
            </g:form>

        </div>
    </body>
</html>
