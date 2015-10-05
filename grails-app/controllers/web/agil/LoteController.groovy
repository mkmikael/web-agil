package web.agil

import web.agil.enums.StatusLote

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LoteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def tiposUnidadeByProduto(Long id, Long unidadeId) {
        def produto = Produto.get(id)
        def unidade
        if (unidadeId) {
            unidade = Unidade.get(unidadeId)
            if ( !(unidade in produto.unidades) )
                unidade = produto.unidades[0]
        } else
            unidade = produto.unidades[0]
        def lote = Lote.executeQuery("select o from Lote o where o.produto = :produto and o.unidade = :unidade " +
                "and o.dataCriacao = (select max(u.dataCriacao) from Lote u where u.produto = :produto and u.unidade = :unidade)",
                [produto: produto, unidade: unidade]).first()
        if (lote)
            lote.vencimento = null
        render template: '/lote/form', model: [lote: lote, tipoUnidadeList: produto?.unidades?.sort()]
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 25, 100)
        def criteria = {
            if (params.search_codigo)
                ilike('codigo', "%${params.search_codigo}%")
            if (params.search_status)
                eq( 'statusLote', StatusLote.valueOf(params.search_status))
            if (params.search_produto)
                produto {
                    ilike('descricao', "%${params.search_produto}%")
                }
        }
        def loteList = Lote.createCriteria().list(params, criteria)
        def loteCount = Lote.createCriteria().count(criteria)
        respond loteList, model:[loteCount: loteCount, statusLote: StatusLote.values()] + params
    }

    def show(Lote lote) {
        respond lote
    }

    def create() {
        respond new Lote(params), model: [produtoList: Produto.list()]
    }

    @Transactional
    def save(Lote lote) {
        if (lote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lote.errors, view:'create'
            return
        }

        lote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lote.label', default: 'Lote'), lote.id])
                redirect lote
            }
            '*' { respond lote, [status: CREATED] }
        }
    }

    def edit(Lote lote) {
        respond lote, model: [produtoList: Produto.list()]
    }

    @Transactional
    def update(Lote lote) {
        if (lote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lote.errors, view:'edit'
            return
        }

        lote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lote.label', default: 'Lote'), lote.id])
                redirect lote
            }
            '*'{ respond lote, [status: OK] }
        }
    }

    @Transactional
    def delete(Lote lote) {

        if (lote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        lote.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'lote.label', default: 'Lote'), lote.id])
                redirect url: "/produto/show/${lote.produto.id}#fragment-2"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def getUnidade(Long id) {
        render Lote.get(id) as JSON
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lote.label', default: 'Lote'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
