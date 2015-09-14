$(function() {
	var count = 0;
	var rowHtml;
	rowHtml = $('#row-pedido tbody').clone();
	$('#row-pedido').remove();

	function updateItem(select) {
		var spanPreco = $(select).parent().parent();
		$.ajax('/web-agil/unidade/getUnidade/' + $(select).val(), 
		{
			success: function(data) {
				spanPreco.find('#preco').html(data.valor);
				console.log(data.valor);
			}
		});
	}

	function calcular(element) {
		var row = $(element).parent().parent();
		var quantidade = row.find('#item\\.quantidade').val();
		var desconto = row.find('#item\\.desconto').val();
		var bonificacao = row.find('#item\\.bonificacao').val();
		var preco = parseInt( row.find('#preco').text() );

		if (quantidade == 0) quantidade = 0
		if (desconto == 0) desconto = 0
		if (bonificacao == 0) bonificacao = 0

		quantidade = parseInt(quantidade);
		bonificacao = parseInt(bonificacao);
		var subtotal = preco * quantidade * ( 1 - ( desconto / 100 ) );
		if (bonificacao + quantidade != 0)
			row.find( '#pv' ).text( ( subtotal / ( bonificacao + quantidade ) ).toFixed(2) , 2 );
		else
			row.find( '#pv' ).text( 0 );
		row.find( '#subtotal' ).text( subtotal.toFixed(2) );

		var sum = 0;
		$('.subtotal').each(function(i, e) {
			sum += parseFloat(e.innerHTML);
		});
		$('#total').text(sum);
	}

	$('button.btn').click(function() {
		var row = rowHtml.clone();
		var inputs = row.find("#item\\.quantidade, #item\\.desconto, #item\\.bonificacao");

		inputs.blur(function() {
			if (this.value == 0) 
				this.value = 0;
		});

		inputs.keyup(function() {
			if (this.value < 0)
				this.value = 0;
			calcular(this);
		});

		row.find("#item\\.unidade\\.id").change(function() {
			updateItem(this);
			calcular(this);
		});

		updateItem(row.find("#item\\.unidade\\.id"));
		$('#itens tbody').append( row.find("tr") );
	});


});