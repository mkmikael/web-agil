<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Agil</title>
    </head>
    <body>
        <fieldset>
            <ul>
                <li>
                    Sistema
                    <ol>
                        <li><g:link url="/system">Informações</g:link> </li>
                        <li><g:link url="/console">Console</g:link> </li>
                    </ol>
                </li>
                <li>
                    Segurança
                    <ol>
                        <li><g:link controller="user">Usuários</g:link> </li>
                    </ol>
                </li>
                <li>
                    Cadastros
                    <ol>
                        <li><g:link controller="cliente">Clientes</g:link> </li>
                        <li><g:link controller="produto">Produtos</g:link> </li>
                        <li><g:link controller="pedido">Pedidos</g:link> </li>
                        <li><g:link controller="fornecedor">Fornecedores</g:link> </li>
                        <li><g:link controller="grupo">Grupos</g:link> </li>
                        <li><g:link controller="prazo">Prazos</g:link> </li>
                    </ol>
                </li>
            </ul>
        </fieldset>
    </body>
</html>
