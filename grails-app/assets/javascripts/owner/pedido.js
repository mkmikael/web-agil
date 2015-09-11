$(function() {
	var count = 0;

	$( '#dialog-pedido' ).dialog({
		modal: true,
		autoOpen: false,
		width: 600,
		heigth: 400,
		title: 'Item',
		buttons: [
			{
				text: 'Salvar',
				click: function() {
					var map = { produto: $(this).find('#produto').val(), prazo: $(this).find('#prazo').val(), quantidade: $(this).find('#quantidade').val()
					, desconto: $(this).find('#desconto').val(), bonificacao: $(this).find('#bonificacao').val(), total: $(this).find('#total').val() }
					$('#itens tbody').appendTo( toRow(map) );
					$( this ).dialog( 'close' );
				}
			}
		]
	});

	$('button.btn').click(function() {
		$('#dialog-pedido').dialog( 'open' );
	});

	$("#quant, #desc, #bonif").keyup(function() {
		var row = $(this).parent().parent().parent();
		var quant = row.find('#quant').val();
		var desc = row.find('#desc').val();
		var bonif = row.find('#bonif').val();
		var preco = parseInt( row.find('#preco').text() );
		if (desc == 0) desc = 0
		if (bonif == 0) bonif = 0
		var subtotal = preco * quant * ( 1 - desc / 100 );
		if ((parseInt(bonif) + parseInt(quant)) != 0)
			row.find( '#pv' ).text( subtotal / ( parseInt(bonif) + parseInt(quant) ) , 2 );
		row.find( '#subtotal' ).text( subtotal );
	});

	function toRow(map) {
		var html = "<tr>"
		+ '<td><input type="hidden" name="itensPedido[' + count + '].unidade.id"/>' + map.produto + '</td>'
		+ '<td><input type="hidden" name="itensPedido[' + count + '].prazo.id"/>' + map.prazo + '</td>'
		+ '<td><input type="hidden" name="itensPedido[' + count + '].quantidade"/>' + map.quantidade + '</td>'
		+ '<td><input type="hidden" name="itensPedido[' + count + '].desconto"/>' + map.desconto + '</td>'
		+ '<td><input type="hidden" name="itensPedido[' + count + '].bonificacao"/>' + map.bonificacao + '</td>'
		+ '<td><input type="hidden" name="itensPedido[' + count + '].total"/>' + map.total + '</td>'
		+ "</tr>";
		count++;
		return html;
	}
});