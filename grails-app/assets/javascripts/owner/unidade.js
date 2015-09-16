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

	$('#produto\\.id').change(function() {
		var id = $('#produto\\.id').val();
		$.ajax('/web-agil/unidade/tiposUnidadeByProduto/' + id, {
			success: function(data) {
				var html = $(data).find('#tipoUnidade').html();
				$('#tipoUnidade').html( html );
			}
		}); // end ajax
	});

	$('#produto\\.id').change();
});
