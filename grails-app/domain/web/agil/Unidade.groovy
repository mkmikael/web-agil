package web.agil

class Unidade {

	String tipo
	Double valor
	Double valorMinimo
	Integer quantidade = 0
	Integer estoque = 0

	static belongsTo = [produto: Produto]
    static constraints = {
    }

    String toString() {
    	produto?.codigo + " - " + produto?.descricao + " - " + tipo
    }
}
