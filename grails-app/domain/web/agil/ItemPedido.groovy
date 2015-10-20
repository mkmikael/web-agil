package web.agil

class ItemPedido {

    transient Boolean calcular = true
    transient Integer estoque = 0
    transient Integer caixas
	Integer quantidade = 0
	Integer bonificacao = 0
	Double desconto = 0
	Double precoNegociado = 0
	Double total = 0
    Double valor
    Double valorMinimo
	Produto produto
    Unidade unidade
    Boolean confirmado = false

	static belongsTo = [pedido: Pedido]
    static constraints = {
    }

	def isAbaixoDoMinimo() {
		precoNegociado < valorMinimo
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

    private calc() {
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
