package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.*;

@Transactional(readOnly = true)
class FornecedorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Fornecedor.list(params), model:[fornecedorCount: Fornecedor.count()]
    }

    def show(Fornecedor fornecedor) {
        respond fornecedor
    }

    def create() {
        respond new Fornecedor(params), model: [grupoList: Grupo.list()]
    }

    @Transactional
    def save(Fornecedor fornecedor) {
        if (fornecedor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (fornecedor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond fornecedor.errors, view:'create'
            return
        }

        fornecedor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fornecedor.label', default: 'Fornecedor'), fornecedor.id])
                redirect fornecedor
            }
            '*' { respond fornecedor, [status: CREATED] }
        }
    }

    def edit(Fornecedor fornecedor) {
        respond fornecedor, model: [grupoList: Grupo.list()]
    }

    @Transactional
    def update(Fornecedor fornecedor) {
        if (fornecedor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (fornecedor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond fornecedor.errors, view:'edit'
            return
        }

        fornecedor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'fornecedor.label', default: 'Fornecedor'), fornecedor.id])
                redirect fornecedor
            }
            '*'{ respond fornecedor, [status: OK] }
        }
    }

    @Transactional
    def delete(Fornecedor fornecedor) {

        if (fornecedor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        fornecedor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'fornecedor.label', default: 'Fornecedor'), fornecedor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fornecedor.label', default: 'Fornecedor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
