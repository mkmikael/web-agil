package web.agil

class Pedido {

	Date dataCriacao
	Date dataFaturamento
	String codigo
	Double total

	static hasMany = [itensPedido: ItemPedido]
	static belongsTo = [cliente: Cliente]
    static constraints = {
    }
}
