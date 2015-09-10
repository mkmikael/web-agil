<%@ import="web.agil.*; web.agil.enums.*" %>

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
<table>
	<thead>
		<th>Produto</th>
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

<div id="form-item">
	<table>
		<tr>
			<td id="produto">
				<g:select name="itensPedido[].unidade.id" from="${Produto.list()}" optionKey="id" optionValue="descricao" style="width: 100%" />
			</td>
			<td id="prazo">
				<g:select name="itensPedido[].prazo.id" from="${Prazo.list()}" optionKey="id" optionValue="periodicidade" style="width: 100%" />
			</td>
			<td id="preco" style="width: 70px">
				R$ <span>10</span>
			</td>
			<td id="quant">
				<g:field type="number" name="itensPedido[].quantidade" style="width: 70px" />
			</td>
			<td id="desc">
				<g:field type="number" name="itensPedido[].desconto" style="width: 70px" />
			</td>
			<td id="bonif">
				<g:field type="number" name="itensPedido[].bonificacao" style="width: 70px" />
			</td>
			<td id="pv" style="width: 70px">
				<g:hiddenField name="itensPedido[].precoNegociado" />
				0
			</td>
			<td id="subtotal" style="width: 70px">
				<g:hiddenField name="itensPedido[].total" />
			</td>
		</tr>
	</table>
</div>