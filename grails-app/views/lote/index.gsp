<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'lote.label', default: 'Lote')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create">Novo Lote</g:link></li>
    </ul>
</div>
<div id="list-lote" class="content scaffold-list" role="main">
    <h1>Lote Listagem</h1>

    <g:form action="index">
        <fieldset class="search">
            <legend>Filtros</legend>
            <label for="search_codigo">Codigo</label>
            <g:textField name="search_codigo" value="${search_codigo}" />

            <label for="search_produto">Produto</label>
            <g:textField name="search_produto" value="${search_produto}" />

            <label for="search_status">Status</label>
            <g:select name="search_status" from="${statusLote}" value="${search_status}" noSelection="['TODOS': 'TODOS']" />
        </fieldset>
        <fieldset>
            <input type="submit" value="Procurar">
        </fieldset>
    </g:form>

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <g:form>
        <fieldset class="search">
            <legend>Operacoes</legend>
            <g:actionSubmit onclick="return confirm('Voce tem certeza?')" value="Deletar" action="deleteAll" />
        </fieldset>
        <br>
        <table>
            <thead>
            <th><input onchange="$('table tbody input:checkbox').prop('checked', $(this).prop('checked'))" type="checkbox"></th>
            <g:sortableColumn params="${params}" property="codigo" title="Codigo" />
            <g:sortableColumn params="${params}" property="dataCriacao" title="Dt. Entrada" />
            <g:sortableColumn params="${params}" property="vencimento" title="Dt. Vencimento" />
            <g:sortableColumn params="${params}" property="estoque" title="Estoque" />
            <g:sortableColumn params="${params}" property="produto.descricao" title="Produto" />
            <g:sortableColumn params="${params}" property="statusLote" title="Status" />
            <g:sortableColumn params="${params}" property="unidade.tipo" title="Unidade" />
            <g:sortableColumn params="${params}" property="valorDeCompra" title="Prc Compra" />
            <g:sortableColumn params="${params}" property="valor" title="Preco" />
            <g:sortableColumn params="${params}" property="valorMinimo" title="Prc Minimo" />
            </thead>
            <tbody>
            <g:each in="${loteList}" var="lote">
                <tr>
                    <td><g:checkBox name="check${lote.id}" /></td>
                    <td><g:link action="show" id="${lote?.id}">${lote?.codigo}</g:link></td>
                    <td><g:formatDate date="${lote?.dataCriacao}" format="dd/MM/yyyy HH:mm" /></td>
                    <td><g:formatDate date="${lote?.vencimento}" format="dd/MM/yyyy" /></td>
                    <td>${lote?.estoque}</td>
                    <td><g:link controller="produto" action="show" id="${lote?.produto?.id}">${lote?.produto}</g:link></td>
                    <td>${lote?.statusLote}</td>
                    <td>${lote?.unidade?.tipo}</td>
                    <td>${g.formatNumber(number: lote?.valorDeCompra, type: 'currency')}</td>
                    <td>${g.formatNumber(number: lote?.valor, type: 'currency')}</td>
                    <td>${g.formatNumber(number: lote?.valorMinimo, type: 'currency')}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:form>

    <div class="pagination">
        <g:paginate total="${loteCount ?: 0}" params="${params}" />
    </div>
</div>
</body>
</html>