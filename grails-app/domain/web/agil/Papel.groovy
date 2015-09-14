package web.agil

class Papel {

	String codigo

	static belongsTo = [participante: Participante]
    static constraints = {
    	codigo nullable: true
    	participante nullable: true
    }
}
