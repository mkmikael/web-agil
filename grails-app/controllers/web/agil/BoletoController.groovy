package web.agil

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import web.agil.enums.StatusBoleto

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BoletoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['permitAll'])
    def list(String codVendedor) {
        def boletoList = Boleto.createCriteria().list {
            if (codVendedor)
                cliente { vendedor { eq('codigo', codVendedor) } }
            'in'('statusBoleto', [StatusBoleto.ABERTO, StatusBoleto.VENCIDO])
        } // end boletoList
        def data = []
        boletoList.each { b ->
            data << [id: b.id, cliente: [id: b.cliente.id], dataCriacao: b.dataCriacao, dataVencimento: b.dataVencimento,
                     valorInicial: b.valorInicial, valorAtualizado: b.valorAtualizado, statusBoleto: b.statusBoleto.toString()]
        }
        render data as JSON
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def filters = {
            if (params.search_statusBoleto)
                eq('statusBoleto', StatusBoleto.valueOf(params.search_statusBoleto))
        }
        def boletoList = Boleto.createCriteria().list(params, filters)
        def boletoCount = Boleto.createCriteria().count(filters)
        respond boletoList, model:[boletoCount: boletoCount]
    }

    def show(Boleto boleto) {
        respond boleto
    }

    def create() {
        respond new Boleto(params)
    }

    @Transactional
    def save(Boleto boleto) {
        if (boleto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (boleto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond boleto.errors, view:'create'
            return
        }

        boleto.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'boleto.label', default: 'Boleto'), boleto.id])
                redirect boleto
            }
            '*' { respond boleto, [status: CREATED] }
        }
    }

    def edit(Boleto boleto) {
        respond boleto
    }

    @Transactional
    def update(Boleto boleto) {
        if (boleto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (boleto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond boleto.errors, view:'edit'
            return
        }

        boleto.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'boleto.label', default: 'Boleto'), boleto.id])
                redirect boleto
            }
            '*'{ respond boleto, [status: OK] }
        }
    }

    @Transactional
    def delete(Boleto boleto) {

        if (boleto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        boleto.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'boleto.label', default: 'Boleto'), boleto.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'boleto.label', default: 'Boleto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
