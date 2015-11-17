package web.agil

import web.agil.enums.*
import web.agil.util.Util

class Pedido {

	Date dataCriacao = new Date()
    Date dataSincronizacao = new Date()
	Date dataFaturamento = new Date()
	String codigo = ""
	Double total = 0
	Double totalAvaliado = 0
    Prazo prazo
    StatusPedido statusPedido = StatusPedido.PENDENTE

	static hasMany = [itensPedido: ItemPedido]
	static belongsTo = [cliente: Cliente]
    static constraints = {
    	codigo nullable: true
    }
    static mapping = {
        itensPedido cascade: 'all-delete-orphan'
    }

    def isEstoqueIndisponivel() {
        def valid = false
        itensPedido.each { valid |= it.isEstoqueIndisponivel() }
        valid
    }

	def isItemAbaixoDoMinimo() {
        def valid = false
		itensPedido.each { valid |= it.isAbaixoDoMinimo() }
		valid
	}

    def isItemBonificado() {
        def valid = false
        itensPedido.each { valid |= it.isBonificado() }
        valid
    }

	def beforeInsert() {
		codigo = Util.generateCodigo(12, this.count() + 1)
	}

    def calcularTotal() {
    	total = 0
    	if (itensPedido) {
    		itensPedido.each {
    			total += it.total
    		}
    	}
    	total
    }
    def calcularTotalAvaliado() {
    	totalAvaliado = 0
    	if (itensPedido) {
    		itensPedido.each {
                if (it.confirmado)
                    totalAvaliado += it.total
    		}
    	}
        totalAvaliado
    }

}
