package web.agil

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.*
import web.agil.enums.*
import web.agil.util.Util

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

        pedido.codigo = Util.generateCodigo(12, Pedido.count() + 1)

        if (params.item?.quantidade?.class?.isArray()) {
            def itens = params.item
            for (i in 0..itens.quantidade.length - 1) {
                def itemPedido = new ItemPedido()
                itemPedido.unidade = Unidade.get(params.item.unidade.id[i] as Long)
                itemPedido.quantidade = params.item.quantidade[i] as Integer
                itemPedido.bonificacao = params.item.bonificacao[i] as Integer
                itemPedido.desconto = params.item.desconto[i] as Double
                pedido.addToItensPedido(itemPedido)
            }
        } else {
            def itemPedido = new ItemPedido()
            itemPedido.unidade = Unidade.get(params.item.unidade.id as Long)
            itemPedido.quantidade = params.item.quantidade as Integer
            itemPedido.bonificacao = params.item.bonificacao as Integer
            itemPedido.desconto = params.item.desconto as Double
            pedido.addToItensPedido(itemPedido)
        }

        pedido.calcularTotal()

        if (pedido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pedido.errors, view:'create'
            return
        }

        pedido.itensPedido.each {
            if (it.hasErrors()) {
                transactionStatus.setRollbackOnly()
                respond it.errors, view:'create'
                return
            }
        }

        pedido.save flush: true, failOnError: true
        println "Pedido ID: ${pedido.id}"

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
