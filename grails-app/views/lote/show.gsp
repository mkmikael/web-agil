<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'lote.label', default: 'Lote')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
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
                        <span class="property-value">${lote?.codigo}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Produto</span>
                        <span class="property-value">${lote?.produto}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Data de Entrada</span>
                        <span class="property-value">${g.formatDate(date: lote?.dataCriacao, format: 'dd/MM/yyyy HH:mm')}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Data de Vencimento</span>
                        <span class="property-value">${g.formatDate(date: lote?.vencimento, format: 'dd/MM/yyyy')}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Status</span>
                        <span class="property-value">${lote?.statusLote}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Tipo</span>
                        <span class="property-value">${lote?.unidade?.tipo}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço de Compra</span>
                        <span class="property-value">R$ ${g.formatNumber(number: lote?.valorDeCompra, type: 'currency')}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço</span>
                        <span class="property-value">R$ ${g.formatNumber(number: lote?.valor, type: 'currency')}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Preço Mínimo</span>
                        <span class="property-value">R$ ${g.formatNumber(number: lote?.valorMinimo, type: 'currency')}</span>
                    </div>
                    <div class="fieldcontain">
                        <span class="property-label">Quant. em Estoque</span>
                        <span class="property-value">${lote?.estoque}</span>
                    </div>
                </div>
                <g:if test="${lote?.statusLote?.toString() == 'ESGOTADO'}">
                    <p >*NAO E PERMITIDO EDICAO DE LOTES ESGOTADOS</p>
                </g:if>
            </fieldset>
            <g:form resource="${this.lote}" method="DELETE">
                <fieldset class="buttons">
                    <g:if test="${lote?.statusLote?.toString() != 'ESGOTADO'}">
                        <g:link class="edit" action="edit" params="[edit: true]" resource="${this.lote}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    </g:if>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
