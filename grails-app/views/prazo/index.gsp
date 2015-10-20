<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'prazo.label', default: 'Prazo')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-prazo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-prazo" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <script>
                $(function() {
                    $('#table-prazos input:checkbox').change(function() {
                        var ativo = this.checked;
                        var id = this.name.replace('ativo', '');
                        $.ajax('/web-agil/prazo/saveAtivo/' + id, {
                            type: "POST",
                            data: { ativo: ativo }
                        }); // end ajax
                    });
                });
            </script>
            <table id="table-prazos">
                <thead>
                    <tr>
                        <th>Parcela(s)</th>
                        <th>Descricao</th>
                        <th>Ativo</th>
                    </tr>
                </thead>
               <tbody>
                    <g:each in="${prazoList}" var="p">
                        <tr>
                            <td><g:link action="show" id="${p.id}">${p.parcela}</g:link></td>
                            <td>${p.periodicidade}</td>
                            <td><g:checkBox name="ativo${p.id}" value="${p.ativo}" /></td>
                        </tr>
                    </g:each>
               </tbody>
            </table>

            <div class="pagination">
                <g:paginate total="${prazoCount ?: 0}" />
            </div>
        </div>
    </body>
</html>