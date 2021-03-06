// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.1.3.js
//= require jquery-ui.min.js
//= require jquery.mask.min.js
//= require jquery.maskMoney.min.js
//= require validar-cnpj-cpf.js
//= require material.js
//= require_self

$(function() {
	$('.uppercase').keyup(function(e) {
		var texto = $(this).val();
		$(this).val(texto.toUpperCase()
			.replace('Á','A')
			.replace('Â','A')
			.replace('À','A')
			.replace('Ã','A')
			.replace('Ê','E')
			.replace('É','E')
			.replace('È','E')
			.replace('Í','I')
			.replace('Î','I')
			.replace('Ì','I')
			.replace('Ó','O')
			.replace('Ô','O')
			.replace('Ò','O')
			.replace('Ô','O')
			.replace('Ú','U')
			.replace('Û','U')
			.replace('Ù','U')
			.replace('Û','U')
		);
	});

	$('.tabs').tabs();
	$('button, input:submit, .btn').addClass('mdl-button mdl-js-button mdl-button--raised mdl-button--colored');

	$('.date').mask('00/00/0000', {placeholder: 'Exemplo 01/01/2016'});
	$('.telefone').mask('(00)0000-0000', {placeholder: 'Exemplo: (91)3212-1234'});
	$('.cpf').mask('000.000.000-00', {placeholder: 'Exemplo: 123.123.123-32'});
	$('.cnpj').mask('00.000.000/0000-00', {placeholder: 'Exemplo: 12.123.123/1234-12'});
	$('.money2').mask('000.000.000.000,00', { reverse: true });
	$(".money").maskMoney({prefix:'R$ ', allowNegative: true, allowZero: true, thousands:'.', decimal:',', affixesStay: false});
    $(".taxa").maskMoney({suffix:'% ', allowNegative: false, allowZero: true, thousands:'.', decimal:',', affixesStay: false});

    $('input:reset, button:reset, .reset').click(function () {
        $('select option').removeAttr('selected');
        $('input:text').val('');
    });
});

if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}
