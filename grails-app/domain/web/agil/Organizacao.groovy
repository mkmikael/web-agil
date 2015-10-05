package web.agil

import web.agil.util.Util

class Organizacao extends Participante {

	String nomeFantasia
	String razaoSocial
	String cnpj
	String inscricaoEstadual

    void setCnpj(String cnpj) {
        this.cnpj = Util.onlyNumber(cnpj)
    }

    String getCnpj() {
        return Util.maskCnpj(cnpj)
    }

    static constraints = {
    	razaoSocial nullable: true
    	cnpj nullable: true
    	inscricaoEstadual nullable: true
    }
}
