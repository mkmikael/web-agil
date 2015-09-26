package web.agil

import web.agil.util.Util

class Produto {

	String codigo
	String descricao

	def beforeInsert = {
		codigo = Util.generateCodigo(12, this.count() + 1)
	}

	static hasMany = [unidades: Unidade, tiposUnidade: TipoUnidade]
	static belongsTo = [fornecedor: Fornecedor, grupo: Grupo]
    static constraints = {
    	codigo nullable: true
    }
    static mapping = {
        unidades sort: 'dataCriacao', order: 'desc'
        tiposUnidade sort: 'tipo'
    }

    String toString() {
    	"$codigo - $descricao"
    }
}
