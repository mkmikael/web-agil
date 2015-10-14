package web.agil

class Unidade {

	TipoUnidade tipo
	Integer capacidade

	static belongsTo = [produto: Produto]
    static constraints = {
    }

    String toString() {
    	tipo
    }
}
