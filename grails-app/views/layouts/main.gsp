<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <g:layoutHead/>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/0.11.2/angular-material.min.css">
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-aria.min.js"></script>


<!-- Angular Material Javascript now available via Google CDN; version 0.11.2 used here -->
<script src="https://ajax.googleapis.com/ajax/libs/angular_material/0.11.2/angular-material.min.js"></script>
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
    <sec:ifLoggedIn>
        <header class="mdl-layout__header">
            <div class="mdl-layout__header-row">
                <!-- Title -->
                <span class="mdl-layout-title">Ágil Distribuições</span>
                <!-- Add spacer, to align navigation to the right -->
                <div class="mdl-layout-spacer"></div>
                <!-- Navigation. We hide it in small screens. -->
                <button id="demo-menu-lower-left"
                        class="mdl-button mdl-js-button mdl-button--icon">
                    <i class="material-icons">more_vert</i>
                </button>

                <div class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="demo-menu-lower-left">
                    <g:link class="mdl-menu__item" controller="lote">Lotes</g:link>
                    <g:link class="mdl-menu__item" controller="pedido">Pedidos</g:link>
                    <g:link class="mdl-menu__item" controller="produto">Produtos</g:link>
                    <g:link class="mdl-menu__item" controller="cliente">Clientes</g:link>
                    <g:link class="mdl-menu__item" controller="logout">Sair</g:link>
                </div>
            </nav>
            </div>
        </header>
        <div class="mdl-layout__drawer">
            <span class="mdl-layout-title">Ágil</span>
            <nav class="mdl-navigation">
                <span class="mdl-navigation__link">Operação</span>
                <g:link class="mdl-navigation__link" controller="boleto">Boletos</g:link>
                <g:link class="mdl-navigation__link" controller="pedido">Pedidos</g:link>
                <g:link class="mdl-navigation__link" controller="lote">Lotes</g:link>
                <div class="android-drawer-separator"></div>
                <span class="mdl-navigation__link">Cadastros</span>
                <g:link class="mdl-navigation__link" controller="produto">Produtos</g:link>
                <g:link class="mdl-navigation__link" controller="cliente">Clientes</g:link>
                <g:link class="mdl-navigation__link" controller="fornecedor">Fornecedores</g:link>
                <g:link class="mdl-navigation__link" controller="grupo">Grupos</g:link>
                <g:link class="mdl-navigation__link" controller="prazo">Prazos</g:link>
                <g:link class="mdl-navigation__link" controller="tributo">Tributos</g:link>
                <div class="android-drawer-separator"></div>
                <span class="mdl-navigation__link">Sistema</span>
                <g:link class="mdl-navigation__link" controller="console">Console</g:link>
                <g:link class="mdl-navigation__link" controller="user">Usuários</g:link>
                <a class="mdl-navigation__link" href="/web-agil/system">Informações</a>
                <div class="android-drawer-separator"></div>
                <g:link class="mdl-navigation__link" controller="logout">Sair</g:link>
            </nav>
        </div>

    </sec:ifLoggedIn>
    <main class="mdl-layout__content">
        <div class="page-content" style="margin: 0 auto; margin: 0 1em">
            <g:layoutBody/>
        </div>
    </main>
</div>
</body>
</html>
