package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CobrancaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cobranca.list(params), model:[cobrancaCount: Cobranca.count()]
    }

    def show(Cobranca cobranca) {
        respond cobranca
    }

    def create() {
        respond new Cobranca(params)
    }

    @Transactional
    def save(Cobranca cobranca) {
        if (cobranca == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cobranca.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cobranca.errors, view:'create'
            return
        }

        cobranca.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cobranca.label', default: 'Cobranca'), cobranca.id])
                redirect cobranca
            }
            '*' { respond cobranca, [status: CREATED] }
        }
    }

    def edit(Cobranca cobranca) {
        respond cobranca
    }

    @Transactional
    def update(Cobranca cobranca) {
        if (cobranca == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cobranca.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cobranca.errors, view:'edit'
            return
        }

        cobranca.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cobranca.label', default: 'Cobranca'), cobranca.id])
                redirect cobranca
            }
            '*'{ respond cobranca, [status: OK] }
        }
    }

    @Transactional
    def delete(Cobranca cobranca) {

        if (cobranca == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        cobranca.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cobranca.label', default: 'Cobranca'), cobranca.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cobranca.label', default: 'Cobranca'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
