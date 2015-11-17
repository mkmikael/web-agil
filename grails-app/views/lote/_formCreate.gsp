<%@ page import="web.agil.Lote" %>
<asset:javascript src="owner/create-lote.js"/>

<style>
    table tbody tr:hover { background-color: transparent }
    table tbody tr input, table tbody tr select { margin: 0; background-color: white }
</style>

<table>
    <thead>
        <th>Produto</th>
        <th>Unidade</th>
        <th>P.Compra</th>
        <th>P.Venda</th>
        <th>P.Min</th>
        <th>Quantidade</th>
        <th>Vencimento</th>
    </thead>
    <tbody>
        <g:set var="lote" value="${Lote.first()}" />
        <g:each in="${1..20}" >
            <tr>
                <td>
                    <g:hiddenField name="id" value="${lote?.id}" />
                    <g:select name="produto.id" value="${lote?.produto?.id}" from="${produtoList}"
                              optionKey="id" required="required" disabled="${params.edit ?: false}" />
                </td>
                <td>
                    <g:select name="unidade.id" value="${lote?.unidade?.id}" from="${lote?.produto?.unidades}"
                              optionKey="id" required="required" disabled="${params.edit ?: false}" />
                </td>
                <td>
                    <g:textField name="valorDeCompra" class="money" required="required"  />
                </td>
                <td>
                    <g:textField name="valor" class="money" required="required" />
                </td>
                <td>
                    <g:textField name="valorMinimo" class="money" required="required" />
                </td>
                <td><g:textField name="estoque" required="required" />
                </td>
                <td><g:textField class="date" name="vencimento" required="required" />
                </td>
            </tr>
        </g:each>
    </tbody>
</table>
