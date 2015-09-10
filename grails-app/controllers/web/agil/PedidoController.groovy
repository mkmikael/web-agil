package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PedidoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Pedido.list(params), model:[pedidoCount: Pedido.count()]
    }

    def show(Pedido pedido) {
        respond pedido
    }

    def create() {
        respond new Pedido(params)
    }

    @Transactional
    def save(Pedido pedido) {
        if (pedido == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pedido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pedido.errors, view:'create'
            return
        }

        pedido.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pedido.label', default: 'Pedido'), pedido.id])
                redirect pedido
            }
            '*' { respond pedido, [status: CREATED] }
        }
    }

    def edit(Pedido pedido) {
        respond pedido
    }

    @Transactional
    def update(Pedido pedido) {
        if (pedido == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pedido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pedido.errors, view:'edit'
            return
        }

        pedido.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pedido.label', default: 'Pedido'), pedido.id])
                redirect pedido
            }
            '*'{ respond pedido, [status: OK] }
        }
    }

    @Transactional
    def delete(Pedido pedido) {

        if (pedido == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pedido.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pedido.label', default: 'Pedido'), pedido.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pedido.label', default: 'Pedido'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
