package web.agil

import web.agil.enums.StatusBoleto

class Boleto {

    StatusBoleto statusBoleto = StatusBoleto.ABERTO
    Date dataCriacao = new Date()
    Date dataVencimento
    Double valorInicial = 0
    Double valorAtualizado = 0

    static belongsTo = [cliente: Cliente]
    static constraints = {
    }
}
