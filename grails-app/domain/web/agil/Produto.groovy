package web.agil

import web.agil.util.Util

class Produto {

	String codigo
	String descricao
    String ncm
    Integer estoque = 0
    static transients = ['estoqueTipo', 'lote']

    def getEstoqueTipo() {
        def q = { base, i ->
            (base - (base % i)) / i
        }
        def show = [:]
        try {
            if (unidades) {
                if (unidades.size() > 1) {
                    def unidade = unidades.find { it.toString() == "CXA" }
                    show = [caixa: q(estoque , unidade.capacidade), unidade: (estoque % unidade.capacidade)]
                } else if (unidades.size() == 1) {
                    def unidade = unidades.first()
                    if (unidade.toString() == "CXA") {
                        show = [caixa: q(estoque , unidade.capacidade), unidade: '']
                    } else {
                        show = [caixa: '', unidade: estoque]
                    }
                }
            }
        } catch (ArithmeticException) {
            show = [caixa: '', unidade: '']
        }
        show
    }

    Lote getLote() {
        if (unidades)
            Lote.findByProdutoAndUnidade(this, unidades.first(), [sort: 'dataCriacao', order: 'desc'])
        else
            null
    }

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
