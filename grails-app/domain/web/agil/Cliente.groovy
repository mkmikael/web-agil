package web.agil

import web.agil.enums.*

class Cliente extends Papel {

	Double limite = 0
	Situacao situacao = Situacao.EM_DIA
	Float rate = 0

	static hasMany = [pedidos: Pedido]
    static constraints = {
    }
}
