$(function() {
	var tags = [ "UNI", "DP", "PT", "CXA" ];
	$( "#tipo" ).autocomplete({
		autoFocus: true,
		source: function( request, response ) {
			var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( request.term ), "i" );
			response( $.grep( tags, function( item ) {
				return matcher.test( item );
			}) );
		}
	});

	$('#produto\\.id, #tipoUnidade\\.id').change(function() {
		var produtoId = $('#produto\\.id').val();
        var id = this.id;
		$.ajax('/web-agil/unidade/tiposUnidadeByProduto/' + produtoId, {
            data: { tipoUnidadeId: $('#tipoUnidade').val() },
			success: function(data) {
                var tipoUnidade = $(data).find('#tipoUnidade\\.id').html();
                $('#tipoUnidade\\.id').html( tipoUnidade );

                var valorMinimo = $(data).find('#valorMinimo').val();
                $('#valorMinimo').val( valorMinimo );

                var valor = $(data).find('#valor').val();
                $('#valor').val( valor );

                var valorDeCompra = $(data).find('#valorDeCompra').val();
                $('#valorDeCompra').val( valorDeCompra );
			}
		}); // end ajax
	});
    window.onload = function() {
	    $('#produto\\.id').change();
    }
});
