package web.agil

import web.agil.enums.*
import web.agil.util.Util

class Lote {

	String codigo
	Date dataCriacao = new Date()
	Date vencimento
	Unidade unidade
	Double valorDeCompra = 0
	Double valor = 0
	Double valorMinimo = 0
	Integer estoque = 0
	StatusLote statusLote = StatusLote.DISPONIVEL
	Lote parent

	static belongsTo = [produto: Produto]
	static hasMany = [movimentos: Movimento]
    static constraints = {
    	codigo nullable: true
    	parent nullable: true
    }

    static namedQueries = {
        currentLote {

        }
    }

	def beforeInsert = {
		codigo = "L" + Util.generateCodigo(12, this.count() + 1)
	}

    String toString() {
    	produto?.codigo + " - " + produto?.descricao + " - " + unidade.tipo
    }

}
