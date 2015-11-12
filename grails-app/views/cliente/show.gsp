<%@ page import="web.agil.*; web.agil.enums.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="choose"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-cliente" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <fieldset>
                <div class="tabs">
                    <ul>
                        <li><a href="#dados">Dados do Cliente</a></li>
                        <li><a href="#pedidos">Pedidos</a></li>
                    </ul>
                    <div id="dados">
                        <div class="property-list">
                            <div class="fieldcontain">
                                <span class="property-label">Codigo</span>
                                <span class="property-value">${cliente?.codigo}</span>
                            </div>
                            <g:if test="${cliente?.participante.class == Organizacao}">
                                <g:render template="/organizacao/show" model="[participante: cliente?.participante]" />
                            </g:if>
                            <g:elseif test="${cliente?.participante.class == Pessoa}">
                                <g:render template="/pessoa/show" model="[participante: cliente?.participante]" />
                            </g:elseif>
                            <div class="fieldcontain">
                                <span class="property-label">Limite</span>
                                <span class="property-value">${cliente?.limite}</span>
                            </div>
                            <div class="fieldcontain">
                                <span class="property-label">Situação</span>
                                <span class="property-value">${cliente?.situacao}</span>
                            </div>
                            <div class="fieldcontain">
                                <span class="property-label">Dia de Rota</span>
                                <span class="property-value">${cliente?.diaDeVisita}</span>
                            </div>
                            <div class="fieldcontain">
                                <span class="property-label">Vendedor</span>
                                <span class="property-value">${cliente?.vendedor?.codigo}</span>
                            </div>
                        </div> <!-- property-list -->
                    </div>
                    <div id="pedidos">
                        <g:link class="btn" controller="pedido" action="create" params="['cliente.id': cliente?.id]">Adicionar Pedido</g:link>
                        <br/><br/>
                        <table>
                            <thead>
                                <th>Código</th>
                                <th>Dt. do Pedido</th>
                                <th>Dt. de Faturamento</th>
                                <th>Total</th>
                            </thead>
                            <tbody>
                                <g:each in="${cliente?.pedidos}" var="pedido">
                                    <tr>
                                        <td>${pedido?.codigo}</td>
                                        <td>${pedido?.dataCriacao}</td>
                                        <td>${pedido?.dataFaturamento}</td>
                                        <td>${pedido?.total}</td>
                                    </tr>
                                </g:each>  
                            </tbody>
                        </table>
                    </div>
                </div>
            </fieldset>
            
            <g:form resource="${this.cliente}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.cliente}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
