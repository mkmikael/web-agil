package web.agil

import web.agil.util.Util

class Produto {

	String codigo
	String descricao
    String ncm
    Integer estoque

	def beforeInsert = {
        if (!codigo)
            codigo = Util.generateCodigo(12, this.count() + 1)
	}

	static hasMany = [lotes: Lote, unidades: Unidade, tributos: ProdutoTributo]
	static belongsTo = [fornecedor: Fornecedor, grupo: Grupo]
    static constraints = {
    	codigo nullable: true
        ncm nullable: true
    }
    static mapping = {
        lotes sort: 'dataCriacao', order: 'desc'
        tributos sort: 'tributo.id'
        unidades sort: 'tipo'
    }

    String toString() {
    	"$codigo - $descricao"
    }
}
