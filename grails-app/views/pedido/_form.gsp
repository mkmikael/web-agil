<style type="text/css">
	table { border: none; }
	table tr:hover { background-color: transparent; }
</style>

<g:hiddenField name="cliente.id" value="${pedido?.cliente?.id ?: params?.cliente?.id}" />

<div class="fieldcontain">
	<label for="dataFaturamento">Data de Faturamento</label>
	<g:datePicker name="dataFaturamento" value="${pedido?.dataFaturamento}" precision="day" />
</div>
<div class="fieldcontain">
	<label for="prazo.id">Prazo</label>
	<g:select name="prazo.id" from="${web.agil.Prazo.list()}" optionKey="id" optionValue="periodicidade" />
</div>

<div style="float: right; font-size: 1.8em">
	Total: R$ <span id="total">0</span>
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
<table id="itens">
	<thead>
		<th>Produto</th>
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

<div id="row-pedido">
	<table>
		<tbody>
			<tr>
				<td><g:select name="item.unidade.id" style="width: 100%" from="${loteList}" optionKey="id" /></td>
				<td>R$ <span id="preco">3</span></td>
				<td><g:field type="number" name="item.quantidade" autocomplete="off" value="0" style="max-width: 70px" /></td>
				<td><g:field type="number" name="item.desconto" autocomplete="off" value="0" style="max-width: 70px" /></td>
				<td><g:field type="number" name="item.bonificacao" autocomplete="off" value="0" style="max-width: 70px" /></td>
				<td>R$ <span id="pv">0</span></td>
				<td>R$ <span id="subtotal" class="subtotal">0</span></td>
			</tr>
		</tbody>
	</table>
</div>