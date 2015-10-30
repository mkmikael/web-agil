package web.agil

import web.agil.enums.*

class Cliente extends Papel {

    String codigo
	Date dataCriacao = new Date()
	Double limite = 0
	Situacao situacao = Situacao.EM_DIA
	Float rate = 0
	Prazo prazo
	Semana diaDeVisita
	TipoAvista tipoAvista = TipoAvista.DINHEIRO // se o prazo escolhido for AVISTA 

	static hasMany = [pedidos: Pedido]
	static belongsTo = [vendedor: Vendedor]
    static constraints = {
    	diaDeVisita nullable: true
    	prazo nullable: true
    	tipoAvista nullable: true
    }

}
