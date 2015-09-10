<g:hiddenField name="participante.id" value="${participanteInstance?.id}" />
<g:hiddenField name="participante.id" value="${participanteInstance?.version ?: 0}" />
<div class="fieldcontain">
	<label for="participante.endereco">Endereço</label>
	<g:textField name="participante.endereco" value="${participanteInstance?.endereco}" size="50" />
</div>
<div class="fieldcontain">
	<label for="participante.cidade">Cidade</label>
	<g:textField name="participante.cidade" value="${participanteInstance?.cidade}" />
</div>
<div class="fieldcontain">
	<label for="participante.bairro">Bairro</label>
	<g:textField name="participante.bairro" value="${participanteInstance?.bairro}" />
</div>
<div class="fieldcontain">
	<label for="participante.referencia">Referência</label>
	<g:textField name="participante.referencia" value="${participanteInstance?.referencia}" size="50" />
</div>
<div class="fieldcontain">
	<label for="participante.email">E-mail</label>
	<g:field type="email" name="participante.email" value="${participanteInstance?.email}" />
</div>
<div class="fieldcontain">
	<label for="participante.telefone">Telefone</label>
	<g:field type="phone" name="participante.telefone" value="${participanteInstance?.telefone}" />
</div>
<div class="fieldcontain">
	<label for="participante.contato">Contato</label>
	<g:textField name="participante.contato" value="${participanteInstance?.contato}" />
</div>