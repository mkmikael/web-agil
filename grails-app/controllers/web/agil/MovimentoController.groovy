package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MovimentoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 30, 100)
        respond Movimento.list(params), model:[movimentoCount: Movimento.count()]
    }

    def show(Movimento movimento) {
        respond movimento
    }

    def create() {
        respond new Movimento(params)
    }

    @Transactional
    def save(Movimento movimento) {
        if (movimento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (movimento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimento.errors, view:'create'
            return
        }

        movimento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'movimento.label', default: 'Movimento'), movimento.id])
                redirect movimento
            }
            '*' { respond movimento, [status: CREATED] }
        }
    }

    def edit(Movimento movimento) {
        respond movimento
    }

    @Transactional
    def update(Movimento movimento) {
        if (movimento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (movimento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond movimento.errors, view:'edit'
            return
        }

        movimento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'movimento.label', default: 'Movimento'), movimento.id])
                redirect movimento
            }
            '*'{ respond movimento, [status: OK] }
        }
    }

    @Transactional
    def delete(Movimento movimento) {

        if (movimento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        movimento.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'movimento.label', default: 'Movimento'), movimento.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimento.label', default: 'Movimento'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
