package web.agil

import web.agil.enums.*

class Movimento {

	Date dataCriacao = new Date()
	TipoMovimento tipo

	static belongsTo = [unidade: Unidade]
    static constraints = {
    }
}
