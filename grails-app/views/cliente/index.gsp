<%@ page import="web.agil.*; web.agil.enums.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <asset:javascript src="/owner/cliente-choose.js" />
    </head>
    <body>
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
                <g:render template="search" />
                <div style="margin: .7em">
                    <input type="submit" value="Procurar" />
                </div>
            </g:form>

            <g:form name="operacaoForm">
                <fieldset class="search">
                    <legend>Operacoes</legend>
                    <button class="btn" type="button" onclick="$('#dialogRota').dialog({title: 'Alterar Cliente'})">Alterar Dias de Rota</button>
                </fieldset>
                <br>
                <div class="scroll-x">
                    <g:if test="${tipoPessoa == "PF"}">
                        <g:render template="/pessoa/list" model="[clienteList: clienteList]" />
                    </g:if>
                    <g:if test="${tipoPessoa == "PJ"}">
                        <g:render template="/organizacao/list" model="[clienteList: clienteList]" />
                    </g:if>
                </div>
            </g:form>

            <div class="pagination">
                <g:paginate total="${clienteCount ?: 0}" params="${params}" />
            </div>

            <div id="dialogRota">
                <script>
                    function updateAllRota() {
                        var isConfim = confirm('Voce tem certeza?');
                        if (isConfim) {
                            var value = $('#inputRota').val();
                            var input = $("<input>")
                                    .attr("type", "hidden")
                                    .attr("name", "rota").val(value);
                            $('#operacaoForm').append(input);
                        }
                        return isConfim;
                    }
                </script>

                <div class="fieldcontain">
                    <label>Dia de Rota</label>
                    <g:select name="inputRota" from="${Semana.values()}" />
                </div>
                <br>
                <g:actionSubmit value="Atualizar" action="updateAllRota" form="operacaoForm" onclick="return updateAllRota()" />
            </div>
        </div>
    </body>
</html>
