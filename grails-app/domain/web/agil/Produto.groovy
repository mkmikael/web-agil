package web.agil

import grails.rest.*
import web.agil.util.Util

class Produto {

	String codigo
	String descricao
    String ncm

	def beforeInsert = {
        if (!codigo)
            codigo = Util.generateCodigo(12, this.count() + 1)
	}

	static hasMany = [unidades: Unidade, tiposUnidade: TipoUnidade, tributos: ProdutoTributo]
	static belongsTo = [fornecedor: Fornecedor, grupo: Grupo]
    static constraints = {
    	codigo nullable: true
        ncm nullable: true
    }
    static mapping = {
        unidades sort: 'dataCriacao', order: 'desc'
        tributos sort: 'tributo.id'
        tiposUnidade sort: 'tipo'
    }

    String toString() {
    	"$codigo - $descricao"
    }
}
