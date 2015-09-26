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
        params.max = Math.min(max ?: 30, 100)
        params.tipoPessoa = params.tipoPessoa ?: 'PF'
        def criteria = {
            participante {
                if (params.tipoPessoa == 'PF') {
                    eq('class', Pessoa.class.getName())
                } else if (params.tipoPessoa == 'PJ') {
                    eq('class', Organizacao.class.getName())
                }
                if (params.search_codigo)
                    ilike('codigo', "%${params.search_codigo}%")
                if (params.search_bairro)
                    ilike('bairro', "%${params.search_bairro}%")
                if (params.search_nome)
                    ilike('nome', "%${params.search_nome}%")
                if (params.search_nomeFantasia)
                    ilike('nomeFantasia', "%${params.search_nomeFantasia}%")
                if (params.search_razaoSocial)
                    ilike('razaoSocial', "%${params.search_razaoSocial}%")
                if (params.search_cpf)
                    ilike('cpf', "%${params.search_cpf}%")
                if (params.search_cnpj)
                    ilike('cnpj', "%${params.search_cnpj}%")
            } // participante
        } // criteria
        def clienteList = Cliente.createCriteria().list(params, criteria)
        def clienteCount = Cliente.createCriteria().count(criteria)
        respond clienteList, model:[clienteCount: clienteCount] + params
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
