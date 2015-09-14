package web.agil

class Unidade {

	TipoUnidade tipoUnidade
	Double valorDeCompra
	Double valor
	Double valorMinimo
	Integer estoque = 0

	static belongsTo = [produto: Produto]
    static constraints = {
    }

    String toString() {
    	produto?.codigo + " - " + produto?.descricao + " - " + tipo
    }
}
