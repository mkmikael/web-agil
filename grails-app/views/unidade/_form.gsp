<g:hiddenField name="id" value="${unidade?.id}" />
<g:if test="${params?.produto?.id}">
	<g:hiddenField name="produto.id" value="${unidade?.produto?.id ?: params?.produto?.id}" />
</g:if>
<g:else>
	<div class="fieldcontain">
		<label for="produto.id">Produto</label>
		<g:select name="produto.id" value="${unidade?.produto?.id}" from="${web.agil.Produto.list()}" optionKey="id" required="required" />
	</div>
</g:else>
<div class="fieldcontain">
	<label for="tipoUnidade.id">Unidade</label>
	<g:select name="tipoUnidade.id" value="${unidade?.tipoUnidade?.id}" from="${tipoUnidadeList}" optionKey="id" required="required" />
</div>
<div class="fieldcontain">
	<label for="valorDeCompra">Preço de Compra</label>
	<g:textField name="valorDeCompra" value="${unidade?.valorDeCompra}" required="required" />
</div>
<div class="fieldcontain">
	<label for="valor">Preço de Venda</label>
	<g:textField name="valor" value="${unidade?.valor}" required="required" />
</div>
<div class="fieldcontain">
	<label for="valorMinimo">Preço Mínimo</label>
	<g:textField name="valorMinimo" value="${unidade?.valorMinimo}" required="required" />
</div>
<div class="fieldcontain">
	<label for="estoque">Quant. Estoque</label>
	<g:textField name="estoque" value="${unidade?.estoque}" required="required" />
	<g:textField name="estoque" value="${unidade?.estoque}" required="required" />
</div>
<div class="fieldcontain">
	<label for="vencimento">Data de Vencimento</label>
	<g:datePicker name="vencimento" value="${unidade?.vencimento}" precision="day" />
</div>
<div class="fieldcontain">
	<label for="statusLote">Status</label>
	<g:select name="statusLote" value="${unidade?.statusLote}" from="${web.agil.enums.StatusLote.values()}" required="required" />
</div>
