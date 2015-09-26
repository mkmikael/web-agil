<fieldset>
    <fieldset class="embedded">
        <legend>Tipo Pessoa</legend>
        <g:radio value="PF" name="tipoPessoa" checked="${tipoPessoa == 'PF'}" /> Pessoa Fisica
        <g:radio value="PJ" name="tipoPessoa" checked="${tipoPessoa == 'PJ'}" /> Pessoa Juridica
    </fieldset>
    <fieldset class="embedded">
        <legend>Geral</legend>
        <label>Código</label>
        <g:textField name="search_codigo" value="${search_codigo}" />

        <label>Bairro</label>
        <g:textField name="search_bairro" value="${search_bairro}" />
    </fieldset>
    <fieldset id="filter-pf" class="embedded">
        <legend>Pessoa Física</legend>
        <label>Nome</label>
        <g:textField name="search_nome" value="${search_nome}" />

        <label>CPF</label>
        <g:textField name="search_cpf" class="cpf" value="${search_cpf}" />
    </fieldset>

    <fieldset id="filter-pj" class="embedded">
        <legend>Pessoa Jurídica</legend>
        <label>Nome Fantasia</label>
        <g:textField name="search_nomeFantasia" value="${search_nomeFantasia}" />

        <label>Razão Social</label>
        <g:textField name="search_razaoSocial" value="${search_razaoSocial}" />

        <label>CNPJ</label>
        <g:textField name="search_cnpj" class="cnpj" value="${search_cnpj}" />
    </fieldset>
</fieldset>