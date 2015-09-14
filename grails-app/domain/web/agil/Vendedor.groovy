package web.agil

class Vendedor extends Papel {

	static hasMany = [clientes: Cliente]
    static constraints = {
    }
}
