package web.agil

class ItemPedido {

    Boolean calcular = true
    Integer estoque = 0
    Integer estoqueAtual

	Integer quantidade = 0
	Integer bonificacao = 0
	Double desconto = 0
	Double precoNegociado = 0
	Double total = 0
    Double valor = 0
    Double valorMinimo = 0
	Produto produto
    Unidade unidade
    Boolean confirmado = true

    static transients = ['estoqueAtual', 'estoque', 'calcular']
	static belongsTo = [pedido: Pedido]
    static constraints = {
    }

    def getEstoqueAtual() {
        try {
            if (unidade.tipo.descricao == "CXA") {
                return produto?.estoqueTipo?.caixa
            } else if (unidade.tipo.descricao == "UNI") {
                return produto?.estoqueTipo?.unidade
            } else {
                return 0
            }
        } catch (Exception e) {
            return 0;
        }
    }

	def isAbaixoDoMinimo() {
		getPrecoNegociado() < valorMinimo
	}

	def isBonificado() {
		bonificacao > 0
	}

    void setQuantidade(Integer quantidade) {
    	this.quantidade = quantidade
        getPrecoNegociado()
    }

    void setBonificacao(Integer bonificacao) {
    	this.bonificacao = bonificacao
        getPrecoNegociado()
    }

    void setDesconto(Double desconto) {
    	this.desconto = desconto
        getPrecoNegociado()
    }

    Double getPrecoNegociado() {
        if (quantidade == 0) return 0
        total = quantidade * valor * ( 1 - ( desconto / 100 ) )
        precoNegociado = total / (bonificacao + quantidade)
        precoNegociado
    }

    def getEstoque() {
        def lotes = Lote.executeQuery("""
                      select sum(l.estoque)
                        from Lote l where l.produto = :produto
                        group by l.unidade""", [produto: this.produto])
        estoque = lotes.first()
        estoque
    }

    def getCaixas() {
        if (unidade.tipo.descricao == 'UNI') {
            return quantidade % unidade.capacidade
        }
        null
    }

    def calc() {
        if (calcular) {
            try {
                if (quantidade == 0) return 0
                total = quantidade * valor * ( 1 - ( desconto / 100 ) )
                precoNegociado = total / (bonificacao + quantidade)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

}
