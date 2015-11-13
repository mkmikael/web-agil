package web.agil

import web.agil.enums.*
import web.agil.util.Util

class Cliente extends Papel {

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
        codigo: nullable: true
    	diaDeVisita nullable: true
    	prazo nullable: true
    	tipoAvista nullable: true
    }

    def beforeInsert = {
        if (!codigo)
            codigo = Util.generateCodigo(12, this.count() + 1)
    }
}
