package web.agil

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.*
import web.agil.util.Util

@Transactional(readOnly = true)
class ProdutoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", saveTributo: "POST"]

    def listJson() {
        def list = []
        Produto.list().each { p ->
            def produto = [:]
            produto.id = p.id
            produto.codigo = p.codigo
            produto.nome = p.descricao
            produto.fornecedor = p.fornecedor?.descricao
            produto.grupo = p.grupo?.descricao
            def unidades = []
            p.unidades?.each { u ->
                def unidade = [:]
                unidade.id = u.id
                unidade.tipo = u.tipoUnidade?.tipo
                unidade.valor = u.valor
                unidade.valorMinimo = u.valorMinimo
                unidades << unidade
            }
            produto.unidades = unidades
            list << produto
        }
        render list as JSON
    }

    @Transactional
    def saveTributo(Long id, Double taxa) {
        def tributo = ProdutoTributo.get(id)
        tributo.taxa = taxa
        render { "OK" }
    }

    def index() {
        params.max = Math.min(params.max ?: 25, 100)
        params.offset = params.offset ?: 0
        def criteria = {
            if (params.search_descricao)
                ilike('descricao', "%${params.search_descricao}%")
            if (params.search_ncm)
                ilike('ncm', "%${params.search_ncm}%")
            if (params.search_codigo)
                ilike('codigo', "%${params.search_codigo}%")
            if (params.search_fornecedor)
                eq('fornecedor', Fornecedor.get(params.search_fornecedor as Long))
            if (params.search_grupo) 
                eq('grupo', Grupo.get(params.search_grupo as Long))
        }
        def produtoList = Produto.createCriteria().list(params, criteria)
        def produtoCount = Produto.createCriteria().count(criteria)
        respond produtoList, model:[produtoCount: produtoCount], params: params
    }

    @Transactional
    def show(Produto produto) {
        def list = produto.tributos*.tributo*.id
        Tributo.list().each {
            if (!list.contains(it.id)) {
                produto.addToTributos(new ProdutoTributo(produto: produto, tributo: it))
            }
        }
        produto.save()
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
