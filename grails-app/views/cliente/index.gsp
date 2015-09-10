<%@ page import="web.agil.*; web.agil.enums.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-cliente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="choose"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-cliente" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                    <g:sortableColumn property="participante.codigo" title="Código" />
                    <th>CPF/CNPJ</th>
                    <th>Nome</th>
                    <g:sortableColumn property="participante.bairro" title="Bairro" />
                    <g:sortableColumn property="participante.cidade" title="Cidade" />
                    <g:sortableColumn property="participante.endereco" title="Endereço" />
                    <g:sortableColumn property="participante.telefone" title="Telefone" />
                </thead>
                <tbody>
                    <g:each in="${clienteList}" var="cliente">
                        <tr>
                            <td><g:link action="show" id="${cliente?.participante?.id}">${cliente?.participante?.codigo}</g:link></td>
                            <g:if test="${cliente?.participante.class == Organizacao}">
                                <td>${cliente?.participante?.cnpj}</td>
                                <td>${cliente?.participante?.nomeFantasia} - ${cliente?.participante?.razaoSocial}</td>
                            </g:if>
                            <g:elseif test="${cliente?.participante.class == Pessoa}">
                                <td>${cliente?.participante?.cpf}</td>
                                <td>${cliente?.participante?.nome}</td>
                            </g:elseif>
                            <td>${cliente?.participante?.cidade}</td>
                            <td>${cliente?.participante?.bairro}</td>
                            <td>${cliente?.participante?.endereco}</td>
                            <td>${cliente?.participante?.telefone}</td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
            <div class="pagination">
                <g:paginate total="${clienteCount ?: 0}" />
            </div>
        </div>
    </body>
</html>