package web.agil

import web.agil.util.Util

class Pessoa extends Participante 	 {

	String nome
	String cpf

    void setCpf(String cpf) {
        this.cpf = Util.onlyNumber(cpf)
    }

    String getCpf() {
        return Util.maskCpf(cpf)
    }

    static constraints = {
    	nome nullable: true
    	cpf nullable: true
    }

    String toString() {
        nome
    }
}
