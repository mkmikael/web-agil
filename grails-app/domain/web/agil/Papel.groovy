package web.agil

import web.agil.enums.StatusPapel


class Papel {

    StatusPapel status = StatusPapel.ATIVO
    String codigo

	static belongsTo = [participante: Participante]
    static constraints = {
    	participante nullable: true
        codigo nullable: true
        status nullable: true
    }

    def ativar() { status = StatusPapel.ATIVO }
    def inativar() { status = StatusPapel.INATIVO }
    def bloquear() { status = StatusPapel.BLOQUEADO }
}
