<div class="fieldcontain">
    <span class="property-label">Nome</span>
    <span class="property-value">${participante?.nome}</span>
</div>
<div class="fieldcontain">
    <span class="property-label">CPF</span>
    <span class="property-value">${participante?.cpf}</span>
</div>
<g:render template="/participante/show" model="[participante: participante]" />