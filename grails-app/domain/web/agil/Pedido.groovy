package web.agil

import web.agil.enums.*

class Pedido {

	Date dataCriacao = new Date()
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
