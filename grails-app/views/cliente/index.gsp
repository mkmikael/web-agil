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

            <g:form action="index">
                <fieldset>
                    <fieldset class="embedded">
                        <legend>Geral</legend>
                        <label>Código</label>
                        <g:textField name="search_codigo" value="${search_codigo}" />

                        <label>Bairro</label>
                        <g:textField name="search_bairro" value="${search_bairro}" />
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
                    <th>CPF/CNPJ</th>
                    <th>Nome</th>
                    <g:sortableColumn property="participante.cidade" title="Cidade" />
                    <g:sortableColumn property="participante.bairro" title="Bairro" />
                    <g:sortableColumn property="participante.endereco" title="Endereço" />
                    <g:sortableColumn property="participante.telefone" title="Telefone" />
                    <g:sortableColumn property="vendedor.codigo" title="Vendedor" />
                    <g:sortableColumn property="vendedor.diaDeVisita" title="Dia de Rota" />
                </thead>
                <tbody>
                    <g:each in="${clienteList}" var="cliente">
                        <tr>
                            <td><g:link action="show" id="${cliente?.id}">${cliente?.participante?.codigo}</g:link></td>
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
                            <td>${cliente?.vendedor?.codigo}</td>
                            <td>${cliente?.diaDeVisita}</td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
            <div class="pagination">
                <g:paginate total="${clienteCount ?: 0}" params="${params}" />
            </div>
        </div>
    </body>
</html>
