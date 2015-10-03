package web.agil

import web.agil.enums.StatusLote

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UnidadeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def tiposUnidadeByProduto(Long id, Long tipoUnidadeId) {
        def produto = Produto.get(id)
        def tipoUnidade
        if (tipoUnidadeId) {
            tipoUnidade = Unidade.get(tipoUnidadeId)
            if ( !(tipoUnidade in produto.tiposUnidade) )
                tipoUnidade = produto.tiposUnidade[0]
        } else
            tipoUnidade = produto.tiposUnidade[0]
        def unidade = Lote.executeQuery("select o from Lote o where o.produto = :produto and o.tipoUnidade = :tipoUnidade " +
                "and o.dataCriacao = (select max(u.dataCriacao) from Lote u where u.produto = :produto and u.tipoUnidade = :tipoUnidade)",
                [produto: produto, tipoUnidade: tipoUnidade])[0]
        if (unidade)
            unidade.vencimento = null
        render template: '/unidade/form', model: [unidade: unidade, tipoUnidadeList: produto?.tiposUnidade?.sort()]
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
        def unidadeList = Lote.createCriteria().list(params, criteria)
        def unidadeCount = Lote.createCriteria().count(criteria)
        respond unidadeList, model:[unidadeCount: unidadeCount, statusLote: StatusLote.values()] + params
    }

    def show(Lote unidade) {
        respond unidade
    }

    def create() {
        respond new Lote(params), model: [produtoList: Produto.list()]
    }

    @Transactional
    def save(Lote unidade) {
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
                flash.message = message(code: 'default.created.message', args: [message(code: 'unidade.label', default: 'Lote'), unidade.id])
                redirect unidade
            }
            '*' { respond unidade, [status: CREATED] }
        }
    }

    def edit(Lote unidade) {
        respond unidade, model: [produtoList: Produto.list()]
    }

    @Transactional
    def update(Lote unidade) {
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
                flash.message = message(code: 'default.updated.message', args: [message(code: 'unidade.label', default: 'Lote'), unidade.id])
                redirect unidade
            }
            '*'{ respond unidade, [status: OK] }
        }
    }

    @Transactional
    def delete(Lote unidade) {

        if (unidade == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        unidade.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'unidade.label', default: 'Lote'), unidade.id])
                redirect url: "/produto/show/${unidade.produto.id}#fragment-2"
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'unidade.label', default: 'Lote'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
