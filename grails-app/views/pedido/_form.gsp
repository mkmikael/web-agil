<%@ page import="web.agil.Cliente; web.agil.ItemPedido" %>
<style type="text/css">
	table { border: none; text-align: center; vertical-align: middle  }
	table tr:hover { background-color: transparent; }
    .toolkit { position: fixed; bottom: 20px; padding: 10px; left: 40%; border: 1px solid #006dba; background-color: #fff; z-index: 10 }
</style>

<g:if test="${pedido?.cliente?.id}">
    <g:hiddenField name="cliente.id" value="${pedido?.cliente?.id}" />
</g:if>
<g:else>
    <div class="fieldcontain">
        <label for="cliente.id">Cliente</label>
        <g:select from="${Cliente.list()}" name="cliente.id" optionKey="id" optionValue="participante" />
    </div>
</g:else>

<div class="fieldcontain">
	<label for="dataFaturamento">Data de Faturamento</label>
	<g:datePicker name="dataFaturamento" value="${pedido?.dataFaturamento}" precision="day" />
</div>
<div class="fieldcontain">
	<label for="prazo.id">Prazo</label>
	<g:select name="prazo.id" from="${prazoList}" optionKey="id" optionValue="periodicidade" />
</div>

<div style="float: right; font-size: 1.8em">
	Total R$ <span id="total" style="font-size: 1.8em">${g.formatNumber(number: pedido.total, format: '#,##0.00') ?: 0}</span>
</div>
<br/><br/>
<p style="font-size: 0.8em">*PV = Preço de Venda</p>
<p style="font-size: 0.8em">*PP = Preço Praticado</p>
<div class="toolkit">
    Produto <input id="produtoFilter">
    Itens Vendidos <input id="vendidosFilter" type="checkbox">
</div>
<table id="itens">
	<thead>
        <tr>
            <th>Produto</th>
            <th>Unidade</th>
            <th>Preço</th>
            <th>Preço Minimo</th>
            <th>Quant.</th>
            <th>Desconto</th>
            <th>Bonificação</th>
            <th>PP</th>
            <th>PV</th>
            <th>Total</th>
        </tr>
	</thead>
	<tbody>
        <g:each in="${itensList}" var="item" status="i">
            <tr style="background-color: ${i % 2 == 0 ? transparent : '#efefef'}">
                <td>
                    <g:hiddenField name="item.id" value="${item.id}" />
                    <g:hiddenField name="item.produto.id" value="${item.produto.id}" />${item.produto.descricao}
                </td>
                <td><g:select name="item.unidade.id" from="${item.produto.unidades}" value="${item.unidade.id}" optionKey="id" style="width: 100%"></g:select></td>
                <td>R$ <g:hiddenField name="item.valor" value="${item.valor}" /><span id="preco">${item.valor}</span></td>
                <td>R$ <g:hiddenField name="item.valorMinimo" value="${item.valorMinimo}" /><span id="precoMinimo">${item.valorMinimo}</span></td>
                <td><g:textField name="item.quantidade" value="${item.quantidade}" autocomplete="off" style="max-width: 70px" /></td>
                <td><g:textField name="item.desconto" value="${item.desconto}" autocomplete="off" style="max-width: 70px" /></td>
                <td><g:textField name="item.bonificacao" value="${item.bonificacao}" autocomplete="off" style="max-width: 70px" /></td>
                <td>R$ <span id="pp">0</span></td>
                <td>R$ <span id="pv">0</span></td>
                <td>R$ <span id="subtotal" class="subtotal">0</span></td>
            </tr>
        </g:each>
	</tbody>
</table>
