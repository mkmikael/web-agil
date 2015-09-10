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

	static hasMany = [papeis: Papel]

    static constraints = {
    	referencia nullable: true
    	telefone nullable: true
    	email nullable: true
    	contato nullable: true
    }
}
