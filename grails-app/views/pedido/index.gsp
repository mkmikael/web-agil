<%@ page import="web.agil.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'pedido.label', default: 'Pedido')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-pedido" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <g:form action="index">
        <fieldset class="embedded" style="padding: 1em; margin: 1em">
            <legend>Filtro Cliente</legend>
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
                <g:textField name="search_cpf" class="cpf" value="${search_cpf}" />
            </fieldset>

            <fieldset class="embedded">
                <legend>Pessoa Jurídica</legend>
                <label>Nome Fantasia</label>
                <g:textField name="search_nomeFantasia" value="${search_nomeFantasia}" />

                <label>Razão Social</label>
                <g:textField name="search_razaoSocial" value="${search_razaoSocial}" />

                <label>CNPJ</label>
                <g:textField name="search_cnpj" class="cnpj" value="${search_cnpj}" />
            </fieldset>
        </fieldset>

        <fieldset class="embedded" style="padding: 1em; margin: 1em">
            <legend>Filtro Pedido</legend>
            <label>Codigo</label>
            <g:textField name="search_codigo_pedido" value="${search_codigo_pedido}" />

            <label>Status</label>
            <g:select name="search_status" from="${statusPedidoList}" value="${search_status}" noSelection="['': 'TODOS']" />
        </fieldset>

        <fieldset>
            <input type="submit" value="Procurar" />
        </fieldset>
    </g:form>

    <g:form name="form-pedidos">
        <fieldset>
            <g:actionSubmit class="btn" value="Negar" action="negarPedidos"/>
            <g:actionSubmit class="btn" value="Confirmar" action="confirmarPedidos"/>
        </fieldset>
        <table id="table-pedido">
            <thead>
            <th><g:checkBox name="geral" /></th>
            <g:sortableColumn params="${params}" property="codigo" title="Código" />
            <g:sortableColumn params="${params}" property="participante.cliente" title="Cliente" />
            <g:sortableColumn params="${params}" property="dataSincronizacao" title="Dt de Sincronizacao" />
            <g:sortableColumn params="${params}" property="dataCriacao" title="Dt do Pedido" />
            <g:sortableColumn params="${params}" property="dataFaturamento" title="Faturamento" />
            <g:sortableColumn params="${params}" property="statusPedido" title="Status" />
            <g:sortableColumn params="${params}" property="prazo" title="Prazo" />
            <g:sortableColumn params="${params}" property="total" title="Total" />
            <th>Abaixo Min.</th>
            <th>Campanha</th>
            </thead>
            <tbody>
            <g:each in="${pedidoList}" var="pedido">
                <tr>
                    <td><g:checkBox name="check${pedido.id}" /></td>
                    <td><g:link action="show" id="${pedido.id}">${pedido.codigo}</g:link></td>
                    <g:if test="${pedido.cliente?.participante.isOrganizacao()}">
                        <td>
                            <g:link controller="cliente" action="show" id="${pedido.cliente?.id}">
                                ${pedido.cliente?.participante?.codigo} - ${pedido.cliente?.participante?.razaoSocial}
                            </g:link>
                        </td>
                    </g:if>
                    <g:elseif test="${pedido.cliente?.participante.isPessoa()}">
                        <td>
                            <g:link controller="cliente" action="show" id="${pedido.cliente?.id}">
                                ${pedido.cliente?.participante?.codigo} - ${pedido.cliente?.participante?.nome}
                            </g:link>
                        </td>
                    </g:elseif>
                    <td>${g.formatDate(date: pedido.dataSincronizacao, format: "dd/MM/yyyy HH:mm")}</td>
                    <td>${g.formatDate(date: pedido.dataCriacao, format: "dd/MM/yyyy HH:mm")}</td>
                    <td>${g.formatDate(date: pedido.dataFaturamento, format: "dd/MM/yyyy")}</td>
                    <td>${pedido.statusPedido}</td>
                    <td>${pedido.prazo?.periodicidade}</td>
                    <td>R$ <g:formatNumber number="${pedido.total}" maxFractionDigits="2" /></td>
                    <td><g:formatBoolean boolean="${pedido.isItemAbaixoDoMinimo()}" /></td>
                    <td><g:formatBoolean boolean="${pedido.isItemBonificado()}" /></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:form>

    <div class="pagination">
        <g:paginate total="${pedidoCount ?: 0}"  params="${params}"/>
    </div>

</div>
</body>
</html>