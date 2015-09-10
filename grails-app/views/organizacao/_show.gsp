<div class="fieldcontain">
	<span class="property-label">Nome Fantasia</span>
	<span class="property-value">${participante?.nomeFantasia}</span>
</div>
<div class="fieldcontain">
	<span class="property-label">Razão Social</span>
	<span class="property-value">${participante?.razaoSocial}</span>
</div>
<div class="fieldcontain">
	<span class="property-label">CNPJ</span>
	<span class="property-value">${participante?.cnpj}</span>
</div>
<div class="fieldcontain">
	<span class="property-label">Inscrição Estadual</span>
	<span class="property-value">${participante?.inscricaoEstadual}</span>
</div>
<g:render template="/participante/show" model="[participante: participante]" />