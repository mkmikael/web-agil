package web.agil

import web.agil.enums.*
import web.agil.util.Util

class Pedido {

	Date dataCriacao = new Date()
    Date dataSincronizacao = new Date()
	Date dataFaturamento = new Date()
	String codigo = ""
	Double total = 0
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

	def isItemAbaixoDoMinimo() {
        def valid = false
		itensPedido.each {
			if (it.isAbaixoDoMinimo() == true)
				valid |= it.isAbaixoDoMinimo()
		}
		valid
	}

    def isItemBonificado() {
        def valid = false
        itensPedido.each {
            if (it.isBonificado())
                valid |= it.isBonificado()
        }
        valid
    }

	def beforeInsert() {
		codigo = Util.generateCodigo(12, this.count() + 1)
	}

    def calcularTotal() {
    	def total = 0
    	if (itensPedido) {
    		itensPedido.each {
    			total += it.total
    		}
    		this.total = total
    	}
    	total
    }

}
