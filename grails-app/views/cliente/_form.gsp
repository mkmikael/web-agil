<%@ page import="web.agil.*; web.agil.enums.*" %>

<g:if test="${clienteInstance.participante.isOrganizacao()}">
	<g:render template="/organizacao/form" model="[participanteInstance: clienteInstance?.participante]" />
</g:if>
<g:elseif test="${clienteInstance.participante.isPessoa()}">
	<g:render template="/pessoa/form" model="[participanteInstance: clienteInstance?.participante]" />
</g:elseif>
<div class="fieldcontain">
	<label for="limite">Limite</label>
	<g:textField name="limite" class="money" value="${clienteInstance?.limite}" />
</div>
<div class="fieldcontain">
	<label for="situacao">Situação</label>
	<g:select name="situacao" value="${clienteInstance?.situacao}" from="${situacaoList}" />
</div>
<div class="fieldcontain">
	<label for="diaDeVisita">Dia de Rota</label>
	<g:select name="diaDeVisita" value="${clienteInstance?.diaDeVisita}" from="${semanaList}" />
</div>
<div class="fieldcontain">
	<label for="vendedor">Vendedor</label>
	<g:select name="vendedor" value="${clienteInstance?.vendedor}" from="${vendedorList}" optionKey="id" optionValue="codigo" />
</div>