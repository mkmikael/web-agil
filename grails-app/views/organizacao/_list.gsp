<table>
    <thead>
    <g:sortableColumn property="participante.codigo" title="Código" />
    <th>Nome Fantasia</th>
    <th>Razao Social</th>
    <g:sortableColumn property="participante.cnpj" title="CNPJ" />
    <g:sortableColumn property="participante.cidade" title="Cidade" />
    <g:sortableColumn property="participante.bairro" title="Bairro" />
    <g:sortableColumn property="participante.endereco" title="Endereço" />
    <th>Telefone</th>
    <g:sortableColumn property="vendedor.codigo" title="Vendedor" />
    <g:sortableColumn property="vendedor.diaDeVisita" title="Dia de Rota" />
    </thead>
    <tbody>
    <g:each in="${clienteList}" var="cliente">
        <tr>
            <td><g:link action="show" id="${cliente?.id}">${cliente?.codigo}</g:link></td>
            <td>${cliente?.participante?.nomeFantasia}</td>
            <td>${cliente?.participante?.razaoSocial}</td>
            <td>${cliente?.participante?.cnpj}</td>
            <td>${cliente?.participante?.cidade}</td>
            <td>${cliente?.participante?.bairro}</td>
            <td>${cliente?.participante?.endereco}</td>
            <td>${cliente?.participante?.telefone}</td>
            <td>${cliente?.vendedor?.codigo}</td>
            <td>${cliente?.diaDeVisita}</td>
        </tr>
    </g:each>
    </tbody>
</table>