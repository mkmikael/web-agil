<f:field bean="fornecedor" property="descricao" label="Descrição"/>
<div class="fieldcontain">
	<label>
		Grupos
	</label>
	<g:each in="${grupoList}" var="grupo" status="i">
    	<g:checkBox name="grupos[${i}]" value="${grupo?.id}" checked="" />${grupo?.descricao}
	</g:each>
</div>