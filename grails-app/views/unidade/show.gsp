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
                        <span class="property-label">Codigo</span>
                        <span class="property-value">${unidade?.codigo}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Produto</span>
                        <span class="property-value">${unidade?.produto}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Data de Entrada</span>
                        <span class="property-value">${g.formatDate(date: unidade?.dataCriacao, format: 'dd/MM/yyyy HH:mm')}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Data de Vencimento</span>
                        <span class="property-value">${g.formatDate(date: unidade?.vencimento, format: 'dd/MM/yyyy')}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Status</span>
                        <span class="property-value">${unidade?.statusLote}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Tipo</span>
                        <span class="property-value">${unidade?.tipoUnidade?.tipo}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço de Compra</span>
                        <span class="property-value">R$ ${g.formatNumber(number: unidade?.valorDeCompra, maxFractionDigits: 2, minFractionDigits: 2)}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço</span>
                        <span class="property-value">R$ ${g.formatNumber(number: unidade?.valor, maxFractionDigits: 2, minFractionDigits: 2)}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço Mínimo</span>
                        <span class="property-value">R$ ${g.formatNumber(number: unidade?.valorMinimo, maxFractionDigits: 2, minFractionDigits: 2)}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Quant. em Estoque</span>
                        <span class="property-value">${unidade?.estoque}</span>
                    </div>
                </div>
                <g:if test="${unidade?.statusLote?.toString() == 'ESGOTADO'}">
                    <p >*NAO E PERMITIDO EDICAO DE LOTES ESGOTADOS</p>
                </g:if>
            </fieldset>
            <g:form resource="${this.unidade}" method="DELETE">
                <fieldset class="buttons">
                    <g:if test="${unidade?.statusLote?.toString() != 'ESGOTADO'}">
                        <g:link class="edit" action="edit" params="[edit: true]" resource="${this.unidade}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    </g:if>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
