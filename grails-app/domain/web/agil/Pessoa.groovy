package web.agil

class Pessoa extends Participante 	 {

	String nome
	String cpf

    static constraints = {
    	nome nullable: true
    	cpf nullable: true
    }
}
