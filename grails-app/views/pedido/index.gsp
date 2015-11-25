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
        <g:if test="${flash.message.class == java.util.ArrayList}">
            <g:each in="${flash.message}" var="message">
                <div class="message" role="status">${message}</div>
            </g:each>
        </g:if>
        <g:else>
            <div class="message" role="status">${flash.message}</div>
        </g:else>
    </g:if>

    <fieldset class="search">
        <legend>Filtros</legend>
        <g:form action="index">
            <g:render template="/cliente/search" />

            <fieldset class="search">
                <legend>Filtro Pedido</legend>
                <label>Codigo</label>
                <g:textField name="search_codigo_pedido" value="${search_codigo_pedido}" />

                <label>Status</label>
                <g:select name="search_status" from="${statusPedidoList}" value="${search_status}" noSelection="['': 'TODOS']" />
            </fieldset>

            <fieldset>
                <button type="button" class="btn reset">Limpar</button>
                <input type="submit" value="Procurar" />
            </fieldset>
        </g:form>
    </fieldset>

    <g:form class="scroll-x" name="form-pedidos">
        <fieldset class="search">
            <legend>Operacoes</legend>
            <g:actionSubmit value="Negar" action="negarPedidos"/>
            <g:actionSubmit value="Confirmar" action="confirmarPedidos"/>
            <g:actionSubmit value="Desfazer" action="desfazerPedidos"/>
            <g:actionSubmit onclick="return confirm('Voce tem certeza?')" value="Deletar" action="deleteAll" />
        </fieldset>
        <br>

        <table id="table-pedido">
            <thead>
            <th><input type="checkbox" onchange="$('#table-pedido tbody td input').prop('checked', $(this).prop('checked'))" /></th>
            <g:sortableColumn params="${params}" property="codigo" title="Código" />
            <g:sortableColumn params="${params}" property="cliente" title="Cliente" />
            <g:sortableColumn params="${params}" property="dataSincronizacao" title="Dt de Sincronizacao" />
            <g:sortableColumn params="${params}" property="dataCriacao" title="Dt do Pedido" />
            <g:sortableColumn params="${params}" property="dataFaturamento" title="Faturamento" />
            <g:sortableColumn params="${params}" property="statusPedido" title="Status" />
            <g:sortableColumn params="${params}" property="prazo" title="Prazo" />
            <g:sortableColumn params="${params}" property="total" title="Total" />
            <th>Abaixo Min.</th>
            <th>Campanha</th>
            <th>Estoque Indisponivel</th>
            </thead>
            <tbody>
            <g:each in="${pedidoList}" var="pedido">
                <tr>
                    <td><g:checkBox name="check${pedido.id}" /></td>
                    <td><g:link action="show" id="${pedido.id}">${pedido.codigo}</g:link></td>
                    <g:if test="${pedido.cliente?.participante.isOrganizacao()}">
                        <td>
                            <g:link controller="cliente" action="show" id="${pedido.cliente?.id}">
                                ${pedido.cliente?.codigo} - ${pedido.cliente?.participante?.razaoSocial}
                            </g:link>
                        </td>
                    </g:if>
                    <g:elseif test="${pedido.cliente?.participante.isPessoa()}">
                        <td>
                            <g:link controller="cliente" action="show" id="${pedido.cliente?.id}">
                                ${pedido.cliente?.codigo} - ${pedido.cliente?.participante?.nome}
                            </g:link>
                        </td>
                    </g:elseif>
                    <td>${g.formatDate(date: pedido.dataSincronizacao, format: "dd/MM/yyyy HH:mm")}</td>
                    <td>${g.formatDate(date: pedido.dataCriacao, format: "dd/MM/yyyy HH:mm")}</td>
                    <td>${g.formatDate(date: pedido.dataFaturamento, format: "dd/MM/yyyy")}</td>
                    <td>${pedido.statusPedido}</td>
                    <td class="text-center">${pedido.prazo?.periodicidade}</td>
                    <td>R$ <g:formatNumber number="${pedido.total}" maxFractionDigits="2" /></td>
                    <td class="text-center"><asset:image src="${pedido.isItemAbaixoDoMinimo() ? 'error' : 'ok'}.png" /> </td>
                    <td class="text-center"><asset:image src="${pedido.isItemBonificado() ? 'error' : 'ok'}.png" /> </td>
                    <td class="text-center"><asset:image src="${pedido.isEstoqueIndisponivel() ? 'error' : 'ok'}.png" /> </td>
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