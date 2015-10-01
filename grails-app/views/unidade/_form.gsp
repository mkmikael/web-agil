<g:hiddenField name="id" value="${unidade?.id}" />
<g:if test="${params?.produto?.id}">
	<g:hiddenField name="produto.id" value="${unidade?.produto?.id ?: params?.produto?.id}" />
</g:if>
<g:else>
	<div class="fieldcontain">
		<label for="produto.id">Produto</label>
		<g:select name="produto.id" value="${unidade?.produto?.id}" from="${produtoList}"
				  optionKey="id" required="required" disabled="${params.edit ?: false}" />
	</div>
</g:else>
<div class="fieldcontain">
	<label for="tipoUnidade.id">Unidade</label>
	<g:select name="tipoUnidade.id" value="${unidade?.tipoUnidade?.id}" from="${tipoUnidadeList}"
			  optionKey="id" required="required" disabled="${params.edit ?: false}" />
</div>
<div class="fieldcontain">
	<label for="valorDeCompra">Preço de Compra</label>
	<g:textField name="valorDeCompra" class="money" required="required"
				 value="${g.formatNumber(number: unidade?.valorDeCompra, minFractionDigits: 2)}" />
</div>
<div class="fieldcontain">
	<label for="valor">Preço de Venda</label>
	<g:textField name="valor" class="money" required="required"
				 value="${g.formatNumber(number: unidade?.valor, minFractionDigits: 2)}"/>
</div>
<div class="fieldcontain">
	<label for="valorMinimo">Preço Mínimo</label>
	<g:textField name="valorMinimo" class="money" required="required"
				 value="${g.formatNumber(number: unidade?.valorMinimo, minFractionDigits: 2)}"/>
</div>
<div class="fieldcontain">
	<label for="estoque">Quant. Estoque</label>
	<g:textField name="estoque" value="${unidade?.estoque}" required="required" />
</div>
<div class="fieldcontain">
	<label for="vencimento">Data de Vencimento</label>
	<g:datePicker name="vencimento" value="${unidade?.vencimento}" precision="day" />
</div>
<div class="fieldcontain">
	<label for="statusLote">Status</label>
    <select id="statusLote" name="statusLote" value="${unidade?.statusLote}" required="required">
        <option value="DISPONIVEL">DISPONIVEL</option>
        <option value="BLOQUEADO">BLOQUEADO</option>
    </select>
</div>
