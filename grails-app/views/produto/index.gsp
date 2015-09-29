<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-produto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-produto" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:form action="index" method="GET">
                <fieldset class="form">
                    <label>Código</label>
                    <g:textField name="search_codigo" value="${search_codigo}" />

                    <label>NCM</label>
                    <g:textField name="search_ncm" value="${search_ncm}" />

                    <label>Descrição</label>
                    <g:textField name="search_descricao" value="${search_descricao}" />

                    <label>Fornecedor</label>
                    <g:select name="search_fornecedor" value="${search_fornecedor}" from="${web.agil.Fornecedor.list()}" optionKey="id" optionValue="descricao" noSelection="['': 'Selecione']"/>

                    <label>Grupo</label>
                    <g:select name="search_grupo" value="${search_grupo}" from="${web.agil.Grupo.list()}" optionKey="id" optionValue="descricao" noSelection="['': 'Selecione']" />
                </fieldset>
                <fieldset>
                    <input type="submit" value="Procurar" />
                </fieldset>
            </g:form>
            <g:form name="form-info-cxa" action="informarCXA" method="post">
                <g:hiddenField name="quantidade" value="0"/>
                <g:hiddenField name="acao" value="0"/>
                <table>
                    <thead>
                        <g:sortableColumn property="codigo" title="Código" />
                        <g:sortableColumn property="ncm" title="NCM" />
                        <g:sortableColumn property="descricao" title="Descrição" />
                        <g:sortableColumn property="fornecedor.descricao" title="Fornecedor" />
                        <g:sortableColumn property="grupo.descricao" title="Grupo" />
                    </thead>
                    <tbody>
                        <g:each in="${produtoList}" var="produto">
                            <tr style="background-color: ${quantidade == 0 ? 'yellow' : 'transparent'}">
                                <td><g:link action="show" id="${produto?.id}">${produto?.codigo}</g:link></td>
                                <td>${produto?.ncm}</td>
                                <td>${produto?.descricao}</td>
                                <td>${produto?.fornecedor?.descricao}</td>
                                <td>${produto?.grupo?.descricao}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </g:form>
            <div class="pagination">
                <g:paginate total="${produtoCount ?: 0}" params="${params}" />
            </div>

        </div>
    </body>
</html>