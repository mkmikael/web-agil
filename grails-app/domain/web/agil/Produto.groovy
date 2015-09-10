package web.agil

import web.agil.util.Util

class Produto {

	String codigo
	String descricao

	def beforeInsert = {
		codigo = Util.generateCodigo(8, this.count() + 1)
	}

	static hasMany = [unidades: Unidade]
	static belongsTo = [fornecedor: Fornecedor, grupo: Grupo]
    static constraints = {
    	codigo nullable: true
    }

    String toString() {
    	"$codigo - $descricao"
    }
}
