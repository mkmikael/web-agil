package web.agil

class Unidade {

	TipoUnidade tipo
	Integer capacidade
	Unidade superior
	Unidade inferior

	static belongsTo = [produto: Produto]
    static constraints = {
    	superior nullable: true
    	inferior nullable: true
    }

    String toString() {
    	tipo
    }
}
