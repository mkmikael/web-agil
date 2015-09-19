package web.agil

class ItemPedido {

	Integer quantidade = 0
	Integer bonificacao = 0
	Double desconto = 0
	Double precoNegociado = 0
	Double total = 0
	Unidade unidade

	static belongsTo = [pedido: Pedido]
    static constraints = {
    }

	def isAbaixoDoMinimo() {
		precoNegociado < unidade.valorMinimo
	}

	def isBonificado() {
		bonificacao > 0
	}

    void setQuantidade(Integer quantidade) {
    	this.quantidade = quantidade
    	calc()
    }

    void setBonificacao(Integer bonificacao) {
    	this.bonificacao = bonificacao
    	calc();
    }

    void setDesconto(Double desconto) {
    	this.desconto = desconto
    	calc();
    }

    private calc() {
    	if (quantidade == 0 || !unidade) return 0
    	total = quantidade * unidade?.valor * ( 1 - ( desconto / 100 ) )
    	precoNegociado = total / (bonificacao + quantidade)
    }

}
