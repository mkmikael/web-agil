/**
 * Created by Avell G1511 on 30/10/2015.
 */

$(function() {
    $('input[name="create"]').click(function () {
        var b = confirm('Voce tem certeza?');
        if (b) {
            $('table tbody tr').each(function () {
                var quant = $(this).find('#estoque').val();
                if (quant == 0 || quant == "")
                    $(this).remove();
            }); // end each
        } // end if
        return b;
    });

    $('#produto\\.id, #unidade\\.id').change(function() {
        var id = this.id;
        var parent = $(this).parent().parent();
        var produtoId = parent.find('#produto\\.id').val();
        var unidade = parent.find('#unidade\\.id').val();
        $.ajax('/web-agil/lote/tiposUnidadeByProduto/' + produtoId, {
            data: { unidadeId: unidade },
            success: function(data) {
                if (id != "unidade.id") {
                    var unidade = $(data).find('#unidade\\.id').html();
                    parent.find('#unidade\\.id').html( unidade );
                }

                var valorMinimo = $(data).find('#valorMinimo').val();
                parent.find('#valorMinimo').val( valorMinimo );

                var valor = $(data).find('#valor').val();
                parent.find('#valor').val( valor );

                var valorDeCompra = $(data).find('#valorDeCompra').val();
                parent.find('#valorDeCompra').val( valorDeCompra );
            },
            error: function(ex , m, ex2) {
                console.log("deu merda");
            }
        }); // end ajax
    });

});
