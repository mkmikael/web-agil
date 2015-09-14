<%@ import="web.agil.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pedido.label', default: 'Pedido')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-pedido" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
               <!-- <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li> -->
            </ul>
        </div>
        <div id="list-pedido" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:form action="index">
                <fieldset>
                    <fieldset class="embedded">
                        <legend>Geral</legend>
                        <label>Código</label>
                        <g:textField name="search_codigo" value="${search_codigo}" />
                    </fieldset>

                    <fieldset class="embedded">
                        <legend>Pessoa Física</legend>
                        <label>Nome</label>
                        <g:textField name="search_nome" value="${search_nome}" />

                        <label>CPF</label>
                        <g:textField name="search_cpf" value="${search_cpf}" />
                    </fieldset>

                    <fieldset class="embedded">
                        <legend>Pessoa Jurídica</legend>
                        <label>Nome Fantasia</label>
                        <g:textField name="search_nomeFantasia" value="${search_nomeFantasia}" />

                        <label>Razão Social</label>
                        <g:textField name="search_razaoSocial" value="${search_razaoSocial}" />

                        <label>CNPJ</label>
                        <g:textField name="search_cnpj" value="${search_cnpj}" />
                    </fieldset>
                </fieldset>
                <fieldset>
                    <input type="submit" value="Procurar" />
                </fieldset>
            </g:form>

            <table>
                <thead>
                    <g:sortableColumn property="participante.codigo" title="Código" />
                    <g:sortableColumn property="participante.cliente" title="Cliente" />
                    <g:sortableColumn property="participante.dataCriacao" title="Dt do Pedido" />
                    <g:sortableColumn property="participante.dataFaturamento" title="Faturamento" />
                    <g:sortableColumn property="participante.prazo" title="Prazo" />
                    <g:sortableColumn property="participante.total" title="Total" />
                </thead>
                <tbody>
                    <g:each in="${pedidoList}" var="pedido">
                        <tr>
                            <td><g:link action="show" id="${pedido.cliente?.id}">${pedido.codigo}</g:link></td>
                            <g:if test="${pedido.cliente?.participante.class == Organizacao}">
                                <td>
                                    <g:link controller="cliente" action="show" id="${pedido.cliente?.id}">${pedido.cliente?.participante?.codigo} - ${pedido.cliente?.participante?.razaoSocial}</g:link>
                                </td>
                            </g:if>
                            <g:elseif test="${pedido.cliente?.participante.class == Pessoa}">
                                <td>
                                    <g:link controller="cliente" action="show" id="${pedido.cliente?.id}">
                                        ${pedido.cliente?.participante?.codigo} - ${pedido.cliente?.participante?.nome}
                                    </g:link>
                                </td>
                            </g:elseif>
                            <td>${g.formatDate(date: pedido.dataCriacao, format: "dd/MM/yyyy HH:mm")}</td>
                            <td>${g.formatDate(date: pedido.dataFaturamento, format: "dd/MM/yyyy")}</td>
                            <td>${pedido.prazo?.periodicidade}</td>
                            <td>${pedido.total}</td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
            <div class="pagination">
                <g:paginate total="${pedidoCount ?: 0}"  params="${params}"/>
            </div>
        </div>
    </body>
</html>