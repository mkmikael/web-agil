<%@ page import="web.agil.*; web.agil.enums.*" %>

<g:if test="${clienteInstance?.participante.class == Organizacao}">
	<g:render template="/organizacao/form" model="[participanteInstance: clienteInstance?.participante]" />
</g:if>
<g:elseif test="${clienteInstance?.participante.class == Pessoa}">
	<g:render template="/pessoa/form" model="[participanteInstance: clienteInstance?.participante]" />
</g:elseif>
<div class="fieldcontain">
	<label for="limite">Limite</label>
	<g:field name="limite" type="number" value="${clienteInstance?.limite}" />
</div>
<div class="fieldcontain">
	<label for="situacao">Situação</label>
	<g:select name="situacao" value="${clienteInstance?.situacao}" from="${Situacao.values()}" keys="${Situacao.values()*.name()}" />
</div>