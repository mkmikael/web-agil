package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class VendedorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Vendedor.list(params), model:[vendedorCount: Vendedor.count()]
    }

    def show(Vendedor vendedor) {
        respond vendedor
    }

    def create() {
        respond new Vendedor(params)
    }

    @Transactional
    def save(Vendedor vendedor) {
        if (vendedor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vendedor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vendedor.errors, view:'create'
            return
        }

        vendedor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), vendedor.id])
                redirect vendedor
            }
            '*' { respond vendedor, [status: CREATED] }
        }
    }

    def edit(Vendedor vendedor) {
        respond vendedor
    }

    @Transactional
    def update(Vendedor vendedor) {
        if (vendedor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vendedor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vendedor.errors, view:'edit'
            return
        }

        vendedor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), vendedor.id])
                redirect vendedor
            }
            '*'{ respond vendedor, [status: OK] }
        }
    }

    @Transactional
    def delete(Vendedor vendedor) {

        if (vendedor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        vendedor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), vendedor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
