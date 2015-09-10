package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class GrupoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Grupo.list(params), model:[grupoCount: Grupo.count()]
    }

    def show(Grupo grupo) {
        respond grupo
    }

    def create() {
        respond new Grupo(params)
    }

    @Transactional
    def save(Grupo grupo) {
        if (grupo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (grupo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupo.errors, view:'create'
            return
        }

        grupo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'grupo.label', default: 'Grupo'), grupo.id])
                redirect grupo
            }
            '*' { respond grupo, [status: CREATED] }
        }
    }

    def edit(Grupo grupo) {
        respond grupo
    }

    @Transactional
    def update(Grupo grupo) {
        if (grupo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (grupo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupo.errors, view:'edit'
            return
        }

        grupo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'grupo.label', default: 'Grupo'), grupo.id])
                redirect grupo
            }
            '*'{ respond grupo, [status: OK] }
        }
    }

    @Transactional
    def delete(Grupo grupo) {

        if (grupo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        grupo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'grupo.label', default: 'Grupo'), grupo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'grupo.label', default: 'Grupo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
