package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TributoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tributo.list(params), model:[tributoCount: Tributo.count()]
    }

    def show(Tributo tributo) {
        respond tributo
    }

    def create() {
        respond new Tributo(params)
    }

    @Transactional
    def save(Tributo tributo) {
        if (tributo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tributo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tributo.errors, view:'create'
            return
        }

        tributo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tributo.label', default: 'Tributo'), tributo.id])
                redirect tributo
            }
            '*' { respond tributo, [status: CREATED] }
        }
    }

    def edit(Tributo tributo) {
        respond tributo
    }

    @Transactional
    def update(Tributo tributo) {
        if (tributo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tributo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tributo.errors, view:'edit'
            return
        }

        tributo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tributo.label', default: 'Tributo'), tributo.id])
                redirect tributo
            }
            '*'{ respond tributo, [status: OK] }
        }
    }

    @Transactional
    def delete(Tributo tributo) {

        if (tributo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tributo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tributo.label', default: 'Tributo'), tributo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tributo.label', default: 'Tributo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
