<%@ page import="web.agil.*; web.agil.enums.*;" %>

<div class="fieldcontain">
	<label for="participante.nomeFantasia">Nome Fantasia</label>
	<g:textField name="participante.nomeFantasia" value="${participanteInstance?.nomeFantasia}" size="50" required="required" />
</div>
<div class="fieldcontain">
	<label for="participante.razaoSocial">Razão Social</label>
	<g:textField name="participante.razaoSocial" value="${participanteInstance?.razaoSocial}" size="50" required="required" />
</div>
<div class="fieldcontain">
	<label for="participante.cnpj">CNPJ</label>
	<g:textField name="participante.cnpj" class="cnpj" value="${participanteInstance?.cnpj}" required="required" />
</div>
<div class="fieldcontain">
	<label for="participante.inscricaoEstadual">Inscrição Estadual</label>
	<g:textField name="participante.inscricaoEstadual" value="${participanteInstance?.inscricaoEstadual}" />
</div>
<g:render template="/participante/form" model="[participanteInstance: participanteInstance]" />