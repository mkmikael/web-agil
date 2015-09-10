<div class="fieldcontain">
	<label for="participante.nome">Nome</label>
	<g:textField name="participante.nome" value="${participanteInstance?.nome}" size="50" />
</div>
<div class="fieldcontain">
	<label for="participante.cpf">CPF</label>
	<g:textField name="participante.cpf" value="${participanteInstance?.cpf}" />
</div>
<g:render template="/participante/form" model="[participanteInstance: participanteInstance]" />