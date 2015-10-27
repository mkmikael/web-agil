<!doctype html>
<html lang="en" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><g:layoutTitle default="Grails"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>

        <g:layoutHead/>
    </head>
    <body>
        %{--<div id="grailsLogo" role="banner">--}%
            %{--<a href="http://grails.org"><asset:image src="grails_logo.png" alt="Grails"/></a>--}%
            %{--<sec:ifLoggedIn>--}%
                %{--<div style="float: right;  color: black; font-size 1.3em; font-weight: 600; margin: 1.8em 2.5em">--}%
                    %{--Bem Vindo, <sec:loggedInUserInfo field="username"/>--}%
                    %{--<g:link style="margin: 0" controller="logout">--}%
                        %{--<img src="https://cdn1.iconfinder.com/data/icons/CrystalClear/22x22/actions/exit.png" />--}%
                    %{--</g:link>--}%
                %{--</div>--}%
            %{--</sec:ifLoggedIn>--}%
        %{--</div>--}%
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <header class="mdl-layout__header">
                <div class="mdl-layout__header-row">
                    <!-- Title -->
                    <span class="mdl-layout-title">Agil Distribuicoes</span>
                    <!-- Add spacer, to align navigation to the right -->
                    <div class="mdl-layout-spacer"></div>
                    <!-- Navigation. We hide it in small screens. -->
                        <g:link class="mdl-navigation__link" controller="lote">Lotes</g:link>
                        <g:link class="mdl-navigation__link" controller="pedido">Pedidos</g:link>
                        <g:link class="mdl-navigation__link" controller="produto">Estoque</g:link>
                        <g:link class="mdl-navigation__link" controller="cliente">Clientes</g:link>
                    </nav>
                </div>
            </header>
            <div class="mdl-layout__drawer">
                <span class="mdl-layout-title">Agil</span>
                <nav class="mdl-navigation">
                    <g:link class="mdl-navigation__link" controller="cliente">Clientes</g:link>
                    <g:link class="mdl-navigation__link" controller="produto">Estoque</g:link>
                    <g:link class="mdl-navigation__link" controller="lote">Lotes</g:link>
                    <g:link class="mdl-navigation__link" controller="pedido">Pedidos</g:link>
                    <g:link class="mdl-navigation__link" controller="fornecedor">Fornecedores</g:link>
                    <g:link class="mdl-navigation__link" controller="grupo">Grupos</g:link>
                    <g:link class="mdl-navigation__link" controller="prazo">Prazos</g:link>
                    <g:link class="mdl-navigation__link" controller="tributo">Tributos</g:link>
                    <g:link class="mdl-navigation__link" controller="console">Console</g:link>
                    <g:link class="mdl-navigation__link" controller="user">Usuários</g:link>
                    <a class="mdl-navigation__link" href="/web-agil/system">Informações</a>
                </nav>
            </div>
            <main class="mdl-layout__content">
                <div class="page-content" style="margin: 0 auto; margin: 0 1em">
                    <g:layoutBody/>
                </div>
            </main>
        </div>
    </body>
</html>
