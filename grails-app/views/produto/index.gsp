<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="Estoque" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create">Novo Produto</g:link></li>
                <li><g:link class="create" controller="lote" action="create">Novo Lote</g:link></li>
            </ul>
        </div>
        <div id="list-produto" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:form action="index">
                <fieldset class="search">
                    <div class="fieldcontain">
                        <label>Código</label>
                        <g:textField name="search_codigo" value="${search_codigo}" />
                    </div>

                    <div class="fieldcontain">
                        <label>NCM</label>
                        <g:textField name="search_ncm" value="${search_ncm}" />
                    </div>

                    <div class="fieldcontain">
                        <label>Descrição</label>
                        <g:textField name="search_descricao" value="${params.search_descricao}" size="40" />
                    </div>

                    <div class="fieldcontain">
                        <label>Fornecedor</label>
                        <g:select name="search_fornecedor" value="${search_fornecedor}" from="${fornecedorList}" optionKey="id" optionValue="descricao" noSelection="['': 'Selecione']"/>
                    </div>

                    <div class="fieldcontain">
                        <label>Grupo</label>
                        <g:select name="search_grupo" value="${search_grupo}" from="${grupoList}" optionKey="id" optionValue="descricao" noSelection="['': 'Selecione']" />
                    </div>
                </fieldset>
                <fieldset>
                    <input type="submit" value="Procurar" />
                </fieldset>
            </g:form>
            <div class="scroll-x">
                <table>
                    <thead>
                        <g:sortableColumn params="${params}" property="codigo" title="Código" />
                        <g:sortableColumn params="${params}" property="ncm" title="NCM" />
                        <g:sortableColumn params="${params}" property="descricao" title="Descrição" />
                        <g:sortableColumn params="${params}" property="fornecedor.descricao" title="Fornecedor" />
                        <g:sortableColumn params="${params}" property="grupo.descricao" title="Grupo" />
                        <g:sortableColumn params="${params}" property="estoque" title="CXA" />
                        <th>UNI</th>
                    </thead>
                    <tbody>
                        <g:each in="${produtoList}" var="produto" status="i">
                            <tr>
                                <td><g:link action="show" id="${produto?.id}">${produto?.codigo}</g:link></td>
                                <td>${produto?.ncm}</td>
                                <td>${produto?.descricao}</td>
                                <td>${produto?.fornecedor?.descricao}</td>
                                <td>${produto?.grupo?.descricao}</td>
                                <td>${produto?.estoqueTipo?.caixa}</td>
                                <td>${produto?.estoqueTipo?.unidade}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="pagination">
                <g:paginate total="${produtoCount ?: 0}" params="${params}" />
            </div>
        </div>
    </body>
</html>
