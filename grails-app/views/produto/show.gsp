<!DOCTYPE html>
<asset:javascript src="produto.js" />
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-produto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-produto" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset>
                <div class="tabs">
                    <ul>
                        <li><a href="#produto">Produto</a></li>
                        <li><a href="#lotes">Lotes</a></li>
                        <li><a href="#tributos">Tributos</a></li>
                        <li><a href="#unidades">Unidades</a></li>
                    </ul>
                    <div id="produto">
                        <div class="property-list">
                            <div class="fieldcontain">
                                <span class="property-label">Código</span>
                                <span class="property-value">${produto?.codigo}</span>
                            </div>
                            <div class="fieldcontain">
                                <span class="property-label">Descrição</span>
                                <span class="property-value">${produto?.descricao}</span>
                            </div>
                            <div class="fieldcontain">
                                <span class="property-label">Fornecedor</span>
                                <span class="property-value">${produto?.fornecedor}</span>
                            </div>
                            <div class="fieldcontain">
                                <span class="property-label">Grupo</span>
                                <span class="property-value">${produto?.grupo}</span>
                            </div>
                        </div>
                    </div>
                    <div id="lotes">
                        <g:link controller="lote" action="create" class="btn" params="['produto.id': produto?.id]">Novo Lote</g:link>
                        <br/><br/>
                        <table>
                            <thead>
                                <th>Codigo</th>
                                <th>Dt. Entrada</th>
                                <th>Tipo</th>
                                <th>Preço Compra</th>
                                <th>Preço</th>
                                <th>Preço Mínimo</th>
                                <th>Quant. em Estoque</th>
                                <th>Status</th>
                            </thead>
                            <tbody>
                                <g:each in="${produto?.lotes}" var="lote">
                                    <tr>
                                        <td><g:link controller="lote" action="show" id="${lote.id}">${lote?.codigo}</g:link></td>
                                        <td>${g.formatDate(date: lote?.dataCriacao, format: 'dd/MM/yyyy')}</td>
                                        <td>${lote?.unidade?.tipo}</td>
                                        <td><g:formatNumber number="${lote?.valorDeCompra}" type="currency"/></td>
                                        <td><g:formatNumber number="${lote?.valor}" type="currency"/></td>
                                        <td><g:formatNumber number="${lote?.valorMinimo}" type="currency"/></td>
                                        <td>${lote?.estoque}</td>
                                        <td>${lote?.statusLote}</td>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>

                    <div id="tributos">
                        <fieldset>
                            <table style="width: auto">
                                <thead>
                                    <tr>
                                        <th>Tributo</th>
                                        <th>Taxa</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${produto.tributos}" var="produtoTributo" status="i">
                                        <tr>
                                            <td>${produtoTributo.tributo?.descricao}</td>
                                            <td><g:textField name="tributo" onblur="saveTributo(this, ${produtoTributo.id})" class="taxa" value="${g.formatNumber(number: produtoTributo.taxa, type: 'currency', currencySymbol: '')}" size="5" /></td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </fieldset>
                    </div>

                    <div id="unidades">
                        <fieldset>
                            <table style="width: auto">
                                <thead>
                                    <tr>
                                        <th>Tipo</th>
                                        <th>Capacidade</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${produto.unidades}" var="unidade" status="i">
                                        <tr>
                                            <td>${unidade.tipo}</td>
                                            <td>${unidade.capacidade}</td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </fieldset>
                    </div> <!-- end tab tributos -->

                </div> <!-- tabs -->
            </fieldset>
            <g:form resource="${this.produto}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.produto}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
