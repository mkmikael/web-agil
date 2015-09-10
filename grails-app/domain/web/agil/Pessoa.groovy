package web.agil

class Pessoa extends Participante {

	String nome
	String cpf

    static constraints = {
    	cpf nullable: true
    }
}
