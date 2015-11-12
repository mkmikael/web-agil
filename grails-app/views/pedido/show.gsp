<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pedido.label', default: 'Pedido')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-pedido" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-pedido" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <ul class="property-list">
                <li class="fieldcontain">
                    <span class="property-label">Status</span>
                    <span class="property-value">${pedido.statusPedido}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Código do Pedido</span>
                    <span class="property-value">${pedido.codigo}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Cliente</span>
                    <span class="property-value">
                        <g:if test="${pedido.cliente?.participante.class == web.agil.Organizacao}">
                            ${pedido.cliente?.participante?.razaoSocial}
                        </g:if>
                        <g:elseif test="${pedido.cliente?.participante.class == web.agil.Pessoa}">
                            ${pedido.cliente?.participante?.nome}
                        </g:elseif>
                    </span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Data de Sincrinizacao</span>
                    <span class="property-value">${g.formatDate(date: pedido.dataSincronizacao, format: "dd/MM/yyyy HH:mm")}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Data do Pedido</span>
                    <span class="property-value">${g.formatDate(date: pedido.dataCriacao, format: "dd/MM/yyyy HH:mm")}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Data de Faturamento</span>
                    <span class="property-value">${g.formatDate(date: pedido.dataFaturamento, format: "dd/MM/yyyy")}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Prazo</span>
                    <span class="property-value">${pedido.prazo?.periodicidade}</span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Total</span>
                    <span class="property-value">R$ <g:formatNumber number="${pedido.total}" maxFractionDigits="2" /></span>
                </li><li class="fieldcontain">
                    <span class="property-label">Total Avaliado</span>
                    <span class="property-value">R$ <span id="totalAvaliado"><g:formatNumber number="${pedido.totalAvaliado}" maxFractionDigits="2" /></span></span>
                </li>
            </ul>

            <g:form id="${pedido.id}">
                <fieldset>
                    <g:actionSubmit class="btn" onclick="return confirm('Voce tem certeza?')" value="Negar" action="negarPedido"  />
                    <g:actionSubmit class="btn" onclick="return confirm('Voce tem certeza?')" value="Confirmar" action="confirmarPedido" />
                    <g:actionSubmit class="btn" onclick="return confirm('Voce tem certeza?')" value="Desfazer" action="desfazerPedido" />
                </fieldset>
            </g:form>

            <table id="itens">
                <thead>
                    <th>Produto</th>
                    <th>Unidade</th>
                    <th>Quant.(Q)</th>
                    <th>Bonif.(B)</th>
                    <th>Q+B</th>
                    <th>Estoque</th>
                    <th>Nv Estoque</th>
                    <th>Desconto</th>
                    <th>Preço</th>
                    <th>Preço Minimo</th>
                    <th>PP</th>
                    <th>PV</th>
                    <th>Total</th>
                    <th>Abaixo Min</th>
                    <th>Confirmado</th>
                </thead>
                <tbody>
                    <g:each in="${pedido.itensPedido.sort{ it.produto?.descricao } }" var="item">
                        <g:set var="novoEstoque" value="${item.estoqueAtual  - item?.quantidade}"/>
                        <tr style="background-color: ${item.isAbaixoDoMinimo() || novoEstoque < 0 ? 'rgba(255, 10, 27, 0.79)': 'transparent'}">
                            <td>${item?.produto?.descricao}</td>
                            <td>${item?.unidade}</td>
                            <td>${item?.quantidade}</td>
                            <td>${item?.bonificacao}</td>
                            <td>${item?.bonificacao + item.quantidade}</td>
                            <td>${item.estoqueAtual}</td>
                            <td>${novoEstoque < 0 ? 'INDISPONIVEL' : novoEstoque}</td>
                            <td><g:formatNumber number="${item?.desconto}" maxFractionDigits="2" />%</td>
                            <td>R$ <g:formatNumber number="${item?.valor}" maxFractionDigits="2" /></td>
                            <td>R$ <g:formatNumber number="${item?.valorMinimo}" maxFractionDigits="2" /></td>
                            <td>R$ <g:formatNumber number="${item?.valor - (item?.valor * (item?.desconto / 100))}" maxFractionDigits="2" /></td>
                            <td>R$ <g:formatNumber number="${item?.precoNegociado}" maxFractionDigits="2" /></td>
                            <td>R$ <g:formatNumber number="${item?.total}" maxFractionDigits="2" /></td>
                            <td><g:formatBoolean boolean="${item?.valorMinimo > item?.precoNegociado}" /></td>
                            <td><g:checkBox name="check${item?.id}" value="${item?.confirmado}" /></td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </div>
    </body>
</html>
