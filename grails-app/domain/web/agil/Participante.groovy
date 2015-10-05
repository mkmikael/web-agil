package web.agil

import web.agil.util.Util

class Participante {

	String codigo
	String endereco
	String bairro
	String referencia // ou complemento
	String cidade
	String email
	String telefone
	String contato

    void setTelefone(String telefone) {
        this.telefone = Util.onlyNumber(telefone)
    }

    String getTelefone() {
        telefone
    }

    Boolean isOrganizacao() {
        this.class == Organizacao
    }

    Boolean isPessoa() {
        this.class == Pessoa
    }

	static hasMany = [papeis: Papel]

    static constraints = {
    	endereco nullable: true
    	bairro nullable: true
    	cidade nullable: true
    	referencia nullable: true
    	telefone nullable: true
    	email nullable: true
    	contato nullable: true
    }
}
