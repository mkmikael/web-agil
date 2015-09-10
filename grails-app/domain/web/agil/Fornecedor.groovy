package web.agil

import web.agil.util.Util

class Fornecedor {

	String codigo
	String descricao

	def beforeInsert = {
		codigo = Util.generateCodigo(8, this.count() + 1)
	}

	static hasMany = [produtos: Produto, grupos: Grupo]
    static constraints = {
    	codigo nullable: true
    }

    String toString() {
    	"$codigo - $descricao"
    }
}
