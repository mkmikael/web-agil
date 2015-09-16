package web.agil

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UnidadeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def tiposUnidadeByProduto(Long id) {
        def produto = Produto.get(id)
        render template: '/unidade/form', model: [tipoUnidadeList: produto?.tiposUnidade]
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 25, 100)
        respond Unidade.list(params), model:[unidadeCount: Unidade.count()]
    }

    def show(Unidade unidade) {
        respond unidade
    }

    def create() {
        respond new Unidade(params)
    }

    @Transactional
    def save(Unidade unidade) {
        if (unidade == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (unidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond unidade.errors, view:'create'
            return
        }

        unidade.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'unidade.label', default: 'Unidade'), unidade.id])
                redirect url: "/produto/show/${unidade.produto.id}#fragment-2"
            }
            '*' { respond unidade, [status: CREATED] }
        }
    }

    def edit(Unidade unidade) {
        respond unidade
    }

    @Transactional
    def update(Unidade unidade) {
        if (unidade == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (unidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond unidade.errors, view:'edit'
            return
        }

        unidade.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'unidade.label', default: 'Unidade'), unidade.id])
                redirect url: "/produto/show/${unidade.produto.id}#fragment-2"
            }
            '*'{ respond unidade, [status: OK] }
        }
    }

    @Transactional
    def delete(Unidade unidade) {

        if (unidade == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        unidade.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'unidade.label', default: 'Unidade'), unidade.id])
                redirect url: "/produto/show/${unidade.produto.id}#fragment-2"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def getUnidade(Long id) {
        render Unidade.get(id) as JSON
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'unidade.label', default: 'Unidade'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
