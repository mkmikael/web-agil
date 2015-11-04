package web.agil

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PrazoController {

    static allowedMethods = [saveAtivo: "POST", save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['permitAll'])
    def listJson() {
        def list = []
        Prazo.list().each {
           list << [id: it.id, parcelas: it.parcela, periodicidade: it.periodicidade]
        }
        render list as JSON
    }

    @Transactional
    def saveAtivo(Long id) {
        def prazo = Prazo.get(id)
        prazo.ativo = params.boolean('ativo')
        render 'OK'
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Prazo.list(params), model:[prazoCount: Prazo.count()]
    }

    def show(Prazo prazo) {
        respond prazo
    }

    def create() {
        respond new Prazo(params)
    }

    @Transactional
    def save(Prazo prazo) {
        if (prazo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (prazo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prazo.errors, view:'create'
            return
        }

        prazo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'prazo.label', default: 'Prazo'), prazo.id])
                redirect prazo
            }
            '*' { respond prazo, [status: CREATED] }
        }
    }

    def edit(Prazo prazo) {
        respond prazo
    }

    @Transactional
    def update(Prazo prazo) {
        if (prazo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (prazo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prazo.errors, view:'edit'
            return
        }

        prazo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'prazo.label', default: 'Prazo'), prazo.id])
                redirect prazo
            }
            '*'{ respond prazo, [status: OK] }
        }
    }

    @Transactional
    def delete(Prazo prazo) {

        if (prazo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        prazo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'prazo.label', default: 'Prazo'), prazo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'prazo.label', default: 'Prazo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
