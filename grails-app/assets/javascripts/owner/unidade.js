$(function() {
	var tags = [ "UNI", "DP", "PT", "CXA" ];
	$( "#tipo" ).autocomplete({
		autoFocus: true,
		source: function( request, response ) {
			var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( request.term ), "i" );
			response( $.grep( tags, function( item ){
				return matcher.test( item );
			}) );
		}
	});

});
