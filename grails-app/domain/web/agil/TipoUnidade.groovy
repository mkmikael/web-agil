package web.agil

class TipoUnidade {

	String tipo
	Integer capacidade
	TipoUnidade superior
	TipoUnidade inferior

    static constraints = {
    	superior nullable: true
    	inferior nullable: true
    }
}
