package web.agil

class Papel {

    String codigo

	static belongsTo = [participante: Participante]
    static constraints = {
    	participante nullable: true
    }
}
