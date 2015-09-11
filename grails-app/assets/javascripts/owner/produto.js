$(function() {
	
	$( "#estoque" ).dialog({
		modal: true,
		autoOpen: false,
		title: 'Estoque',
		buttons: [
			{
				text: "Salvar",
				click: function() {
					var quant = $( '#quantidadeEstoque' ).val(); 
					if (quant !== "") {
						$( '#acao' ).val('estoque');
						$( '#quantidade' ).val(quant);
						$( '#form-info-cxa' ).submit();
						$( this ).dialog( "close" );
					}
				}
			}
		]
	});

	$( "#info-cxa" ).dialog({
		modal: true,
		width: 530,
		heigth: 300,
		autoOpen: false,
		title: 'Informar Capacidade CXA',
		buttons: [
			{
				text: "Salvar",
				click: function() {
					var quant = $( '#quantidadeINFO' ).val(); 
					if ( quant !== "" ) {
						$( '#acao' ).val('quantidade');
						$( '#quantidade' ).val(quant);
						$( '#form-info-cxa' ).submit();
						$( this ).dialog( "close" );
					}
				}
			}
		]
	});

});