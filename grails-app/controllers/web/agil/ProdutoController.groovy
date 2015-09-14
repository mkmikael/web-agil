package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.*
import web.agil.util.Util

@Transactional(readOnly = true)
class ProdutoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", informarCXA: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.offset = params.offset ?: 0
        def criteria = {
            produto {
                if (params.search_descricao)
                    ilike('descricao', "%${params.search_descricao}%")
                if (params.search_codigo) 
                    ilike('codigo', "%${params.search_codigo}%")
                if (params.search_fornecedor) 
                    eq('fornecedor', Fornecedor.get(params.search_fornecedor as Long))
                if (params.search_grupo) 
                    eq('grupo', Grupo.get(params.search_grupo as Long))
            }
            if (params.search_tipo)
                eq('tipo', params.search_tipo)
        }
        def unidadeList = Unidade.createCriteria().list(params, criteria)
        def unidadeCount = Unidade.createCriteria().count(criteria)
        respond unidadeList, model:[produtoCount: unidadeCount] + params
    }

    def show(Produto produto) {
        respond produto
    }

    def create() {
        respond new Produto(params)
    }

    @Transactional
    def save(Produto produto) {
        if (produto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        produto.codigo = Util.generateCodigo(12, Produto.count() + 1)

        if (produto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond produto.errors, view:'create'
            return
        }

        produto.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
                redirect produto
            }
            '*' { respond produto, [status: CREATED] }
        }
    }

    def edit(Produto produto) {
        respond produto
    }

    @Transactional
    def update(Produto produto) {
        if (produto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (produto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond produto.errors, view:'edit'
            return
        }

        produto.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
                redirect produto
            }
            '*'{ respond produto, [status: OK] }
        }
    }

    @Transactional
    def delete(Produto produto) {

        if (produto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        produto.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def informarCXA() {
        def ids = [] 
        params.each {
            if ( it.key.startsWith( 'unidade_' ) ) {
                ids << new Long( it.key.replace('unidade_', '') )
            }
        }

        Unidade.getAll(ids).each {
            if (params.acao == "quantidade") {
                it.quantidade = new Integer( params.quantidade )
            } else if (params.acao == "estoque") {
                it.estoque = new Integer( params.quantidade )
            }
        }
        redirect action: 'index', model: params
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
