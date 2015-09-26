<f:field bean="fornecedor" property="descricao" label="Descrição"/>
<div class="fieldcontain">
	<label>
		Grupos
	</label>
    <g:each in="${grupoList}" var="grupo" status="i">
        <div class="property-value">
            <g:checkBox name="grupos[${i}]" value="${grupo?.id}" checked="" />${grupo?.descricao}
        </div>
    </g:each>
</div>