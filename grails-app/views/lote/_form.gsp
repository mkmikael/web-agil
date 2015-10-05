<g:hiddenField name="id" value="${lote?.id}" />
<g:if test="${params?.produto?.id}">
	<g:hiddenField name="produto.id" value="${lote?.produto?.id ?: params?.produto?.id}" />
</g:if>
<g:else>
	<div class="fieldcontain">
		<label for="produto.id">Produto</label>
		<g:select name="produto.id" value="${lote?.produto?.id}" from="${produtoList}"
				  optionKey="id" required="required" disabled="${params.edit ?: false}" />
	</div>
</g:else>
<div class="fieldcontain">
	<label for="unidade.id">Unidade</label>
	<g:select name="unidade.id" value="${lote?.unidade?.id}" from="${tipoUnidadeList}"
			  optionKey="id" required="required" disabled="${params.edit ?: false}" />
</div>
<div class="fieldcontain">
	<label for="valorDeCompra">Preço de Compra</label>
	<g:textField name="valorDeCompra" class="money" required="required"
				 value="${g.formatNumber(number: lote?.valorDeCompra, minFractionDigits: 2)}" />
</div>
<div class="fieldcontain">
	<label for="valor">Preço de Venda</label>
	<g:textField name="valor" class="money" required="required"
				 value="${g.formatNumber(number: lote?.valor, minFractionDigits: 2)}"/>
</div>
<div class="fieldcontain">
	<label for="valorMinimo">Preço Mínimo</label>
	<g:textField name="valorMinimo" class="money" required="required"
				 value="${g.formatNumber(number: lote?.valorMinimo, minFractionDigits: 2)}"/>
</div>
<div class="fieldcontain">
	<label for="estoque">Quant. Estoque</label>
	<g:textField name="estoque" value="${lote?.estoque}" required="required" />
</div>
<div class="fieldcontain">
	<label for="vencimento">Data de Vencimento</label>
	<g:datePicker name="vencimento" value="${lote?.vencimento}" precision="day" />
</div>
<div class="fieldcontain">
	<label for="statusLote">Status</label>
    <select id="statusLote" name="statusLote" value="${lote?.statusLote}" required="required">
        <option value="DISPONIVEL">DISPONIVEL</option>
        <option value="BLOQUEADO">BLOQUEADO</option>
    </select>
</div>
