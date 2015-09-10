$(function() {
	function changePF() {
		var pessoaFisica = $('#cpf'); 
		pessoaFisica.prop('disabled', false);
		pessoaFisica.prop('required', true);
		pessoaFisica.focus();
		
		var pessoaJuridica = $('#cnpj');
		pessoaJuridica.val('');
		pessoaJuridica.prop('disabled', true);
		pessoaJuridica.prop('required', false);
	}

	$('#fisica').prop('checked', true);
	changePF();

	$('#fisica').change(function() {
		changePF();
	});
	$('#juridica').change(function() {
		var pessoaJuridica = $('#cnpj');
		pessoaJuridica.prop('disabled', false);
		pessoaJuridica.prop('required', true);
		pessoaJuridica.focus();
		
		var pessoaFisica = $('#cpf'); 
		pessoaFisica.val('');
		pessoaFisica.prop('disabled', true);
		pessoaFisica.prop('required', false);
	});
});