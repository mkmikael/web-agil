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
        <th>Status</th>
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
                    <g:textField name="valorDeCompra" class="money" required="required"
                                 value="${g.formatNumber(number: lote?.valorDeCompra, minFractionDigits: 2)}" />
                </td>
                <td>
                    <g:textField name="valor" class="money" required="required"
                                 value="${g.formatNumber(number: lote?.valor, minFractionDigits: 2)}"/>
                </td>
                <td>
                    <g:textField name="valorMinimo" class="money" required="required"
                                 value="${g.formatNumber(number: lote?.valorMinimo, minFractionDigits: 2)}"/>
                </td>
                <td><g:textField name="estoque" value="${lote?.estoque}" required="required" />
                </td>
                <td><g:textField class="date" name="vencimento" value="${g.formatDate(date: lote?.vencimento, format: 'dd/MM/yyyy')}" required="required" />
                </td>
                <td>
                    <select id="statusLote" name="statusLote" value="${lote?.statusLote}" required="required">
                        <option value="DISPONIVEL">DISPONIVEL</option>
                        <option value="BLOQUEADO">BLOQUEADO</option>
                    </select>
                </td>
            </tr>
        </g:each>
    </tbody>
</table>
