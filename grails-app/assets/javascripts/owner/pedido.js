$(function() {
    $('#item\\.unidade\\.id').change(function() {
        var row = $(this).parent().parent();
		$.ajax('/web-agil/pedido/precoByProdutoAndUnidade',
		{
            data: {
                produto: row.find('#item\\.produto\\.id').val(),
                unidade: this.value
            },
			success: function(data) {
				row.find('#preco').html(data.valor);
				row.find('#precoMinimo').html(data.valorMinimo);
				row.find('#item\\.valor').val(data.valor);
				row.find('#item\\.valorMinimo').val(data.valorMinimo);
	        	calcular(row);
			}
		}); // end ajax
    }); // end change

    $('#item\\.quantidade, #item\\.desconto, #item\\.bonificacao').keyup(function() {
		var row = $(this).parent().parent();
		calcular(row);
    });

    $('#item\\.quantidade, #item\\.desconto, #item\\.bonificacao').blur(function () {
        if (this.value == 0) {
            this.value = 0;
        }
    });

    function calcular(row) {
        var quantidade = row.find('#item\\.quantidade').val();
        var desconto = row.find('#item\\.desconto').val();
        var bonificacao = row.find('#item\\.bonificacao').val();
        var preco = parseFloat( row.find('#preco').text() );

        if (quantidade == 0) quantidade = 0
        if (desconto == 0) desconto = 0
        if (bonificacao == 0) bonificacao = 0

        quantidade = parseInt(quantidade);
        bonificacao = parseInt(bonificacao);
        desconto = parseFloat(desconto);
        var subtotal = preco * quantidade * ( 1 - ( desconto / 100 ) );
        if (bonificacao + quantidade != 0) {
            row.find( '#pv' ).text( ( subtotal / ( bonificacao + quantidade ) ).toFixed(2) , 2 );
            row.find( '#pp' ).text( ( subtotal / quantidade ).toFixed(2) , 2 );
        } else {
            row.find( '#pv' ).text( 0 );
            row.find( '#pp' ).text( 0 );
        }
        row.find( '#subtotal' ).text( subtotal.toFixed(2) );

        var sum = 0;
        $('.subtotal').each(function(i, e) {
            sum += parseFloat(e.innerHTML);
        });
        $('#total').text(sum.toFixed(2));
    }

    $('table tbody tr td input[name="item.quantidade"]').each(function () {
        if (this.value > 0) {
            var row = $(this).parent().parent();
            calcular(row);
        }
    }); // end each

    $('#produtoFilter').keyup(function() {
        var content = this.value.toUpperCase();
        if (content.length % 2 == 0) {
            $('table tbody tr').each(function() {
                var text = $(this).find('td:nth-child(1)').text();
                if (text.indexOf(content) != -1) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            }); // end each
        }
    });

    $('#geral').change(function() {
        $('table input:checkbox').prop('checked', this.checked);
    }); // end change

    $('#vendidosFilter').change(function() {
        if (this.checked) {
            $('table tbody tr td input[name="item.quantidade"]').each(function () {
                if (this.value == null || this.value == "0")
                    $(this).parent().parent().hide();
            }); // end each
        } else {
            $('table tbody tr').show();
        }
    });

    $('input:submit').click(function () {
        $('table tbody tr td input[name="item.quantidade"]').each(function () {
            if (this.value == null || this.value == "0")
                $(this).parent().parent().remove();
        }); // end each
    }); // end click
});