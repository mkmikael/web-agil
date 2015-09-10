package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.*
import web.agil.enums.*
import web.agil.util.Util

@Transactional(readOnly = true)
class ClienteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", create: ["POST", "GET"]]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cliente.list(params), model:[clienteCount: Cliente.count()]
    }

    def show(Cliente cliente) {
        respond cliente
    }

    def create() {
        def cliente = new Cliente(params)
        if (params.cpf) {
            cliente.participante = new Pessoa(params)
        } else if (params.cnpj) {
            cliente.participante = new Organizacao(params)
        }
        respond cliente
    }

    @Transactional
    def save(Cliente cliente) {
        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (params.participante.cpf) {
            cliente.participante = new Pessoa(params.participante)
        } else if (params.participante.cnpj) {
            cliente.participante = new Organizacao(params.participante)
        }
        cliente.participante.codigo = Util.generateCodigo(12, Participante.count() + 1)

        cliente.participante.save()

        if (cliente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cliente.errors, view:'create'
            return
        }

        cliente.save()

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect cliente
            }
            '*' { respond cliente, [status: CREATED] }
        }
    }

    def edit(Cliente cliente) {
        respond cliente
    }

    @Transactional
    def update(Cliente cliente) {
        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (params.participante.cpf) {
            cliente.participante = Pessoa.get(params.participante.id)
            cliente.participante.properties = params.participante
        } else if (params.participante.cnpj) {
            cliente.participante = Organizacao.get(params.participante.id)
            cliente.participante.properties = params.participante
        }

        if (cliente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cliente.errors, view:'edit'
            return
        }

        cliente.save()

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect cliente
            }
            '*'{ respond cliente, [status: OK] }
        }
    }

    @Transactional
    def delete(Cliente cliente) {

        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        cliente.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def choose() { }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
