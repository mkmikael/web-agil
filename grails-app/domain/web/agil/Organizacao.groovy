package web.agil

class Organizacao extends Participante {

	String nomeFantasia
	String razaoSocial
	String cnpj
	String inscricaoEstadual

    static constraints = {
    	razaoSocial nullable: true
    	cnpj nullable: true
    	inscricaoEstadual nullable: true
    }
}
