package web.agil.enums

enum Situacao {
	EM_DIA('EM DIA'), VENCIDO('VENCIDO'), A_VENCER('A VENCER')

	String descricao

	Situacao(String descricao) {
		this.descricao = descricao
	}

	String toString() {
		descricao
	}
}