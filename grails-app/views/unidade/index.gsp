<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'unidade.label', default: 'Unidade')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create">Novo Lote</g:link></li>
            </ul>
        </div>
        <div id="list-unidade" class="content scaffold-list" role="main">
            <fieldset>
                <g:form action="index">
                    <fieldset class="embedded">
                            <legend>Filtros</legend>
                            <label for="search_codigo">Codigo</label>
                        <g:textField name="search_codigo" value="${search_codigo}" />

                        <label for="search_produto">Produto</label>
                        <g:textField name="search_produto" value="${search_produto}" />

                        <label for="search_status">Status</label>
                        <g:select name="search_status" from="${web.agil.enums.StatusLote.values()}" value="${search_status}" noSelection="['': 'TODOS']" />
                    </fieldset>
                    <fieldset>
                        <input type="submit" value="Procurar">
                    </fieldset>
                </g:form>
            </fieldset>

            <h1>Lote Listagem</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                    <g:sortableColumn property="codigo" title="Codigo" />
                    <g:sortableColumn property="dataCriacao" title="Dt. Entrada" />
                    <g:sortableColumn property="vencimento" title="Dt. Vencimento" />
                    <g:sortableColumn property="estoque" title="Estoque" />
                    <g:sortableColumn property="produto" title="Produto" />
                    <g:sortableColumn property="statusLote" title="Status" />
                    <g:sortableColumn property="tipoUnidade" title="Unidade" />
                    <g:sortableColumn property="valorDeCompra" title="Prc Compra" />
                    <g:sortableColumn property="valor" title="Preco" />
                    <g:sortableColumn property="valorMinimo" title="Prc Minimo" />
                </thead>
                <tbody>
                    <g:each in="${unidadeList}" var="unidade">
                        <tr>
                            <td>${unidade?.codigo}</td>
                            <td><g:formatDate date="${unidade?.dataCriacao}" format="dd/MM/yyyy HH:mm" /></td>
                            <td><g:formatDate date="${unidade?.vencimento}" format="dd/MM/yyyy" /></td>
                            <td>${unidade?.estoque}</td>
                            <td><g:link controller="produto" action="show" id="${unidade?.produto?.id}">${unidade?.produto}</g:link></td>
                            <td>${unidade?.statusLote}</td>
                            <td>${unidade?.tipoUnidade?.tipo}</td>
                            <td>${unidade?.valorDeCompra}</td>
                            <td>${unidade?.valor}</td>
                            <td>${unidade?.valorMinimo}</td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
            <div class="pagination">
                <g:paginate total="${unidadeCount ?: 0}" params="${params}" />
            </div>
        </div>
    </body>
</html>