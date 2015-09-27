<%@ page import="web.agil.*; web.agil.enums.*;" %>

<div class="fieldcontain">
    <label for="ncm">NCM</label>
    <g:textField name="ncm" value="${produto?.ncm}" required="required" />
</div>
<div class="fieldcontain">
	<label for="descricao">Descrição</label>
	<g:textField name="descricao" value="${produto?.descricao}" required="required" size="50" />
</div>
<div class="fieldcontain">
	<label for="fornecedor.id">Fornecedor</label>
	<g:select name="fornecedor.id" value="${produto?.fornecedor?.id}" required="required" from="${Fornecedor.list()}" optionKey="id" optionValue="descricao" noSelection="${['': 'Selecione']}" />
</div>
<div class="fieldcontain">
	<label for="grupo.id">Grupo</label>
	<g:select name="grupo.id" value="${produto?.grupo?.id}" required="required" from="${Grupo.list()}" optionKey="id" optionValue="descricao" noSelection="${['': 'Selecione']}" />
</div>
