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
		var unidade = $('#tipoUnidade\\.id').val();
		$.ajax('/web-agil/unidade/tiposUnidadeByProduto/' + produtoId, {
            data: { tipoUnidadeId: unidade },
			success: function(data) {
				if (id != "tipoUnidade.id") {
					var tipoUnidade = $(data).find('#tipoUnidade\\.id').html();
					$('#tipoUnidade\\.id').html( tipoUnidade );
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
