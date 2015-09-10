<g:hiddenField name="id" value="${unidade?.id}" />
<g:hiddenField name="produto.id" value="${unidade?.produto?.id ?: params?.produto?.id}" />
<div class="fieldcontain">
	<label for="tipo">Tipo</label>
	<g:textField name="tipo" value="${unidade?.tipo}" required="required" />
</div>
<div class="fieldcontain">
	<label for="valor">Preço</label>
	<g:field type="number" name="valor" value="${unidade?.valor}" required="required" />
</div>
<div class="fieldcontain">
	<label for="valorMinimo">Preço Mínimo</label>
	<g:field type="number" name="valorMinimo" value="${unidade?.valorMinimo}" required="required" />
</div>
<div class="fieldcontain">
	<label for="quantidade">Quant. em Unidade</label>
	<g:field type="number" name="quantidade" value="${unidade?.quantidade}" required="required" />
</div>
<div class="fieldcontain">
	<label for="estoque">Quant. em Estoque</label>
	<g:field type="number" name="estoque" value="${unidade?.estoque}" required="required" />
</div>