<%--
  Created by IntelliJ IDEA.
  User: Avell G1511
  Date: 10/10/2015
  Time: 11:03
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main" />
    <title></title>
</head>
<body>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        </ul>
    </div>
    <fieldset>
        <g:form action="listEstoque">
            <fieldset class="embedded" style="padding: 15px">
                <legend>Filtros</legend>
                <label for="search_produto">Produto</label>
                <g:textField name="search_produto" value="${search_produto}" size="40"/>
                <button type="submit">Pesquisar</button>
            </fieldset>
        </g:form>
    </fieldset>
    <div class="pagination">
        <g:paginate total="${estoqueCount}" params="${params}" />
    </div>
    <table>
        <thead>
            <tr>
                <g:sortableColumn title="Codigo" property="produto.codigo" params="${params}" />
                <g:sortableColumn title="Produto" property="produto.descricao" params="${params}" />
                <g:sortableColumn title="NCM" property="produto.ncm" params="${params}" />
                <g:sortableColumn title="Fornecedor" property="produto.fornecedor" params="${params}" />
                <g:sortableColumn title="Grupo" property="produto.grupo" params="${params}" />
                <g:sortableColumn title="Unidade" property="unidade" params="${params}" />
                <g:sortableColumn title="Preco" property="valor" params="${params}" />
                <g:sortableColumn title="Preco Minimo" property="valorMinimo" params="${params}" />
                <g:sortableColumn title="Estoque" property="total" params="${params}" />
            </tr>
        </thead>
        <g:each in="${estoqueList}" var="e">
            <g:set var="produto" value="${e[0]}" />
            <tbody>
                <tr>
                    <td><g:link action="show" id="${produto?.id}">${produto?.codigo}</g:link></td>
                    <td>${produto?.descricao}</td>
                    <td>${produto?.ncm}</td>
                    <td>${produto?.fornecedor?.descricao}</td>
                    <td>${produto?.grupo?.descricao}</td>
                    <td>${e[1]}</td>
                    <td>${g.formatNumber(number: e[2], type: 'currency')}</td>
                    <td>${g.formatNumber(number: e[3], type: 'currency')}</td>
                    <td>${e[4]}</td>
                </tr>
            </tbody>
        </g:each>
    </table>
</body>
</html>