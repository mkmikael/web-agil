$(function() {
	$("#quant, #desc, #bonif").keyup(function() {
		var row = $(this).parent();
		var quant = row.find('#quant input').val();
		var desc = row.find('#desc input').val();
		var bonif = row.find('#bonif input').val();
		var preco = row.find('#preco span').text();
		if (desc == 0) desc = 0
		if (bonif == 0) bonif = 0
		var subtotal = preco * quant * ( 1 - desc / 100 );
		if ((parseInt(bonif) + parseInt(quant)) != 0)
			row.find( '#pv' ).text( subtotal / ( parseInt(bonif) + parseInt(quant) ) , 2 );
		row.find( '#subtotal' ).text( subtotal );
	});
});