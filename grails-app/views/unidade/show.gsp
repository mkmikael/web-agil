<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'unidade.label', default: 'Unidade')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-unidade" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-unidade" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset>
                <div class="property-list">
                    <div class="fieldcontain">
                        <span class="property-label">Tipo</span>
                        <span class="property-value">${unidade?.tipoUnidade?.tipo}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço</span>
                        <span class="property-value">${unidade?.valor}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço Mínimo</span>
                        <span class="property-value">${unidade?.valorMinimo}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Quant. em Unidade</span>
                        <span class="property-value">${unidade?.quantidade}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Quant. em Estoque</span>
                        <span class="property-value">${unidade?.estoque}</span>
                    </div>
                </div>
            </fieldset>
            <g:form resource="${this.unidade}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.unidade}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
