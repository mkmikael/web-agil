<%@ page import="web.agil.Pessoa; web.agil.Organizacao" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pedido.label', default: 'Pedido')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-pedido" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-pedido" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <ul class="property-list">
                <li class="fieldcontain">
                    <span class="property-label">Código do Pedido</span>
                    <span class="property-value">${pedido.codigo}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Cliente</span>
                    <span class="property-value">
                        <g:if test="${pedido.cliente?.participante.class == Organizacao}">
                            ${pedido.cliente?.participante?.razaoSocial}
                        </g:if>
                        <g:elseif test="${pedido.cliente?.participante.class == Pessoa}">
                            ${pedido.cliente?.participante?.nome}
                        </g:elseif>
                    </span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Data do Pedido</span>
                    <span class="property-value">${g.formatDate(date: pedido.dataCriacao, format: "dd/MM/yyyy HH:mm")}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Data de Faturamento</span>
                    <span class="property-value">${g.formatDate(date: pedido.dataFaturamento, format: "dd/MM/yyyy")}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Prazo</span>
                    <span class="property-value">${pedido.prazo?.periodicidade}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Total</span>
                    <span class="property-value">R$ <g:formatNumber number="${pedido.total}" maxFractionDigits="2" /></span>
                </li>
            </ul>

            <table id="itens">
                <thead>
                    <th>Produto</th>
                    <th>Preço</th>
                    <th>Quant.</th>
                    <th>Desconto</th>
                    <th>Bonificação</th>
                    <th>PV</th>
                    <th>Total</th>
                </thead>
                <tbody>
                    <g:each in="${pedido.itensPedido}" var="item">
                        <tr>
                            <td>${item.unidade?.produto?.descricao}</td>
                            <td>R$ <g:formatNumber number="${item.unidade?.valor}" maxFractionDigits="2" /></td>
                            <td>${item?.quantidade}</td>
                            <td><g:formatNumber number="${item?.desconto}" maxFractionDigits="2" />%</td>
                            <td>${item?.bonificacao}</td>
                            <td>R$ <g:formatNumber number="${item?.precoNegociado}" maxFractionDigits="2" /></td>
                            <td>R$ <g:formatNumber number="${item?.total}" maxFractionDigits="2" /></td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
            <g:form resource="${this.pedido}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.pedido}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
