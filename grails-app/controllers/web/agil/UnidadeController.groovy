package web.agil

import web.agil.enums.StatusLote
import web.agil.enums.TipoMovimento

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
            tipoUnidade = TipoUnidade.get(tipoUnidadeId)
            if ( !(tipoUnidade in produto.tiposUnidade) )
                tipoUnidade = produto.tiposUnidade[0]
        } else
            tipoUnidade = produto.tiposUnidade[0]
        def unidade = Unidade.executeQuery("select o from Unidade o where o.produto = :produto and o.tipoUnidade = :tipoUnidade " +
                "and o.dataCriacao = (select max(u.dataCriacao) from Unidade u where u.produto = :produto and u.tipoUnidade = :tipoUnidade)",
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
        def unidadeList = Unidade.createCriteria().list(params, criteria)
        def unidadeCount = Unidade.createCriteria().count(criteria)
        respond unidadeList, model:[unidadeCount: unidadeCount, statusLote: StatusLote.values()] + params
    }

    def show(Unidade unidade) {
        respond unidade
    }

    def create() {
        respond new Unidade(params), model: [produtoList: Produto.list()]
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
                redirect unidade
            }
            '*' { respond unidade, [status: CREATED] }
        }
    }

    def edit(Unidade unidade) {
        respond unidade, model: [produtoList: Produto.list()]
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
                redirect unidade
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
