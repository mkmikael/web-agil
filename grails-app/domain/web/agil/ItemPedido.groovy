package web.agil

class ItemPedido {

    transient Boolean calcular = true
    transient Lote loteTemp
	Integer quantidade = 0
	Integer bonificacao = 0
	Double desconto = 0
	Double precoNegociado = 0
	Double total = 0
	Produto produto
    Unidade unidade

	static belongsTo = [pedido: Pedido]
    static constraints = {
    }

	def isAbaixoDoMinimo() {
		precoNegociado < loteTemp?.valorMinimo
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

    Lote getLoteTemp() {
        if (!loteTemp)
            loteTemp = Lote.executeQuery("from Lote l where l.produto = ? and l.unidade = ? order by l.dataCriacao desc",
                    [produto, unidade], [max: 1]).first()
        loteTemp
    }

    private calc() {
        if (calcular) {
            if (quantidade == 0 && !loteTemp) return 0
            total = quantidade * loteTemp?.valor * ( 1 - ( desconto / 100 ) )
            precoNegociado = total / (bonificacao + quantidade)
        }
    }

}
