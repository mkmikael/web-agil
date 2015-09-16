package web.agil

class TipoUnidade {

	String tipo
	Integer capacidade
	TipoUnidade superior
	TipoUnidade inferior

	static belongsTo = [produto: Produto]
    static constraints = {
    	superior nullable: true
    	inferior nullable: true
    }

    String toString() {
    	tipo
    }
}
