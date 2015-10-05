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

	$('#produto\\.id, #unidade\\.id').change(function() {
		var produtoId = $('#produto\\.id').val();
        var id = this.id;
		var unidade = $('#unidade\\.id').val();
		$.ajax('/web-agil/lote/tiposUnidadeByProduto/' + produtoId, {
            data: { unidadeId: unidade },
			success: function(data) {
				if (id != "unidade.id") {
					var unidade = $(data).find('#unidade\\.id').html();
					$('#unidade\\.id').html( unidade );
				}

                var valorMinimo = $(data).find('#valorMinimo').val();
                $('#valorMinimo').val( valorMinimo );

                var valor = $(data).find('#valor').val();
                $('#valor').val( valor );

                var valorDeCompra = $(data).find('#valorDeCompra').val();
                $('#valorDeCompra').val( valorDeCompra );
			},
			error: function(ex , m, ex2) {
				console.log("deu merda");
			}
		}); // end ajax
	});

	$('#produto\\.id').change();
});
