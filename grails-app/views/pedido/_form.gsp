<%@ import="web.agil.*; web.agil.enums.*" %>

<style type="text/css">
	table { border: none; }
	table tr:hover { background-color: transparent; }
</style>

<g:hiddenField name="dataCriacao" value="${new Date()}" />
<g:hiddenField name="cliente.id" value="${pedido?.cliente?.id ?: params?.cliente?.id}" />

<label for="dataFaturamento">Data de Faturamento</label>
<g:datePicker name="dataFaturamento" value="${pedido?.dataFaturamento}" precision="day" />
<div style="float: right; font-size: 1.8em">
	Total: R$ 1000
</div>
<br/><br/>
<p>
	<button type="button" class="btn" onclick="">Adicionar Item</button>
</p>
<br/>
<p style="font-size: 0.8em">
	*PV = Preço de Venda
</p>
<br/>
<table id="#itens">
	<thead>
		<th>Prazo</th>
		<th>Preço</th>
		<th>Quant.</th>
		<th>Desconto</th>
		<th>Bonificação</th>
		<th>PV</th>
		<th>Total</th>
	</thead>
	<tbody>
		
	</tbody>
</table>

<div id="dialog-pedido">
	<table>
		<tr>
			<td><label>Produto</label></td>
			<td><g:select name="produto" style="width: 100%" from="${Unidade.list()}" optionKey="id" /></td>
		</tr>
		<tr>
			<td>Preço</td>
			<td>R$ <span id="preco">3</span></td>
		</tr>
		<tr>
			<td>Prazo</td>
			<td><g:select name="prazo" from="${Prazo.list()}" optionKey="id" optionValue="periodicidade" /></td>
		</tr>
		<tr>
			<td>Quantidade</td>
			<td><g:field type="number" name="quant" /></td>
		</tr>
		<tr>
			<td>Desconto</td>
			<td><g:field type="number" name="desc" /></td>
		</tr>
		<tr>
			<td>Bonificação</td>
			<td><g:field type="number" name="bonif" /></td>
		</tr>
		<tr>
			<td>PV</td>
			<td>R$ <span id="pv">0</span></td>
		</tr>
		<tr>
			<td>Total</td>
			<td>R$ <span id="subtotal">0</span></td>
		</tr>
	</table>
</div>