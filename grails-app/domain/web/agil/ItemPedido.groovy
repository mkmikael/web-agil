package web.agil

class ItemPedido {

	Integer quantidade
	Integer bonificacao
	Double desconto
	Double precoNegociado
	Double total
	Unidade unidade
	Prazo prazo

	static belongsTo = [pedido: Pedido]
    static constraints = {
    }
}
