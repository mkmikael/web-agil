<g:hiddenField name="participante.id" value="${participanteInstance?.id}" />
<g:hiddenField name="participante.id" value="${participanteInstance?.version ?: 0}" />
<div class="fieldcontain">
	<label for="participante.endereco">Endereço</label>
	<g:textField name="participante.endereco" value="${participanteInstance?.endereco}" size="50" required="required" />
</div>
<div class="fieldcontain">
	<label for="participante.cidade">Cidade</label>
	<g:textField name="participante.cidade" value="${participanteInstance?.cidade}" required="required" />
</div>
<div class="fieldcontain">
	<label for="participante.bairro">Bairro</label>
	<g:textField name="participante.bairro" value="${participanteInstance?.bairro}" required="required" />
</div>
<div class="fieldcontain">
	<label for="participante.referencia">Referência</label>
	<g:textField name="participante.referencia" value="${participanteInstance?.referencia}" size="50" />
</div>
<div class="fieldcontain">
	<label for="participante.email">E-mail</label>
	<g:textField type="email" name="participante.email" value="${participanteInstance?.email}" />
</div>
<div class="fieldcontain">
	<label for="participante.telefone">Telefone</label>
	<g:textField type="phone" name="participante.telefone" minlength="13" class="telefone" value="${participanteInstance?.telefone}" required="required" />
</div>
<div class="fieldcontain">
	<label for="participante.contato">Contato</label>
	<g:textField name="participante.contato" value="${participanteInstance?.contato}" />
</div>