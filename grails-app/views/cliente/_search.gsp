<fieldset class="embedded">
    <legend>Tipo Pessoa</legend>
    <g:radio value="PF" name="tipoPessoa" checked="${tipoPessoa == 'PF'}" /> Pessoa Fisica
    <g:radio value="PJ" name="tipoPessoa" checked="${tipoPessoa == 'PJ'}" /> Pessoa Juridica
</fieldset>
<fieldset class="search">
    <legend>Geral</legend>
    <div class="fieldcontain">
        <label>Código</label>
        <g:textField name="search_codigo" value="${search_codigo}" />
    </div>

    <div class="fieldcontain">
        <label>Bairro</label>
        <g:textField name="search_bairro" value="${search_bairro}" />
    </div>
</fieldset>

<fieldset id="filter-pf" class="search">
    <legend>Pessoa Física</legend>

    <div class="fieldcontain">
        <label>Nome</label>
        <g:textField name="search_nome" value="${search_nome}" />
    </div>

    <div class="fieldcontain">
        <label>CPF</label>
        <g:textField name="search_cpf" class="cpf" value="${search_cpf}" />
    </div>
</fieldset>

<fieldset id="filter-pj" class="embedded">
    <legend>Pessoa Jurídica</legend>
    <div class="fieldcontain">
        <label>Nome Fantasia</label>
        <g:textField name="search_nomeFantasia" value="${search_nomeFantasia}" />
    </div>

    <div class="fieldcontain">
        <label>Razão Social</label>
        <g:textField name="search_razaoSocial" value="${search_razaoSocial}" />
    </div>

    <div class="fieldcontain">
        <label>CNPJ</label>
        <g:textField name="search_cnpj" class="cnpj" value="${search_cnpj}" />
    </div>
</fieldset>