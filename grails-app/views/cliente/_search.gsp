<%@ page import="web.agil.enums.Semana; web.agil.Vendedor" %>
<asset:javascript src="/owner/cliente-choose.js" />
<fieldset class="search">
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

    <div class="fieldcontain">
        <label>Vendedor</label>
        <g:select name="search_vendedor" value="${search_vendedor}" from="${Vendedor.list()}" optionKey="id" noSelection="['': 'TODOS']" />
    </div>

    <div class="fieldcontain">
        <label>Dia de Rota</label>
        <g:select name="search_diaDeVisita" value="${search_diaDeVisita}" from="${Semana.values()}" noSelection="['': 'TODOS']" />
    </div>
</fieldset>

<fieldset id="filter-pf" class="search">
    <legend>Pessoa Física</legend>

    <div class="fieldcontain">
        <label>Nome</label>
        <g:textField name="search_nome" size="35" value="${search_nome}" />
    </div>

    <div class="fieldcontain">
        <label>CPF</label>
        <g:textField name="search_cpf" class="cpf" size="25" value="${search_cpf}" />
    </div>
</fieldset>

<fieldset id="filter-pj" class="search">
    <legend>Pessoa Jurídica</legend>
    <div class="fieldcontain">
        <label>Nome Fantasia</label>
        <g:textField name="search_nomeFantasia" size="35" value="${search_nomeFantasia}" />
    </div>

    <div class="fieldcontain">
        <label>Razão Social</label>
        <g:textField name="search_razaoSocial" size="35" value="${search_razaoSocial}" />
    </div>

    <div class="fieldcontain">
        <label>CNPJ</label>
        <g:textField name="search_cnpj" class="cnpj" size="25" value="${search_cnpj}" />
    </div>
</fieldset>