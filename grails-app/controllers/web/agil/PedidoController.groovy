package web.agil

import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.enums.*

@Transactional(readOnly = true)
class PedidoController {

    static allowedMethods = [savePedidos: "POST", save: "POST", update: "PUT", delete: "DELETE"]

    def savePedidos(String pedidos) {
        def pedidosJA = new JSONArray(pedidos);
        for (JSONObject pedidoJO : pedidosJA) {
            def pedido = new Pedido()
            pedido.dataCriacao = new Date(pedidoJO.getString("dataCriacao"))
            pedido.dataFaturamento = new Date(pedidoJO.getString("dataDeFaturamento"))
            pedido.cliente = Cliente.get(pedidoJO.getLong("cliente"))
            pedido.prazo = Prazo.get(pedidoJO.getLong("prazo"))
            pedido.total = pedidoJO.getDouble("total")

            JSONArray itensJA = pedidoJO.getJSONArray("itensPedido")
            for (JSONObject itemJO : itensJA) {
                def itemPedido = new ItemPedido(
                    bonificacao: itemJO.getInt("bonificacao"),
                    desconto: itemJO.getDouble("desconto"),
                    precoNegociado: itemJO.getDouble("precoNegociado"),
                    quantidade: itemJO.getInt("quantidade"),
                    total: itemJO.getDouble("total"),
                )
                pedido.addToItensPedido(itemPedido)
            }
        }
        println pedidosJson.toString();
        render { "OK!" }
    }

    @Transactional
    def negarPedidos() {
        def ids = []
        params.each { entry ->
            def matcher = entry.key =~ /^check(\d*)/
            if ( matcher.find() )
                ids << (matcher[0][1] as Long)
        }
        Pedido.getAll(ids).each { it.statusPedido = StatusPedido.NEGADO }
        redirect(action: 'index')
    }

    def confirmarPedidos() {
        def ids = []
        params.each { entry ->
            def matcher = entry.key =~ /^check(\d*)/
            if ( matcher.find() )
                ids << (matcher[0][1] as Long)
        }
        Pedido.getAll(ids).each { p ->
            p.statusPedido = StatusPedido.CONFIRMADO
            p.itensPedido.each { item ->
                item.unidade.estoque -= item.quantidade - item.bonificacao
            }
        }
        redirect(action: 'index')
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 30, 100)
        def criteria = {
            if (params.search_codigo_pedido)
                ilike('codigo', "%${params.search_codigo_pedido}%")
            if (params.search_status)
                eq( 'statusPedido',  StatusPedido.valueOf( params.search_status ) )
            cliente {
                participante {
                    if (params.search_codigo)
                        ilike('codigo', "%${params.search_codigo}%")
                    if (params.search_bairro)
                        ilike('bairro', "%${params.search_bairro}%")
                    if (params.search_nome)
                        ilike('nome', "%${params.search_nome}%")
                    if (params.search_nomeFantasia)
                        ilike('nomeFantasia', "%${params.search_nomeFantasia}%")
                    if (params.search_razaoSocial)
                        ilike('razaoSocial', "%${params.search_razaoSocial}%")
                    if (params.search_cpf)
                        ilike('cpf', "%${params.search_cpf}%")
                    if (params.search_cnpj)
                        ilike('cnpj', "%${params.search_cnpj}%")
                } // end participante
            } // end cliente
        } // end criteria
        def pedidoList = Pedido.createCriteria().list(params, criteria)
        def pedidoCount = Pedido.createCriteria().count(criteria)
        respond pedidoList, model:[pedidoCount: pedidoCount] + params
    }

    def show(Pedido pedido) {
        respond pedido
    }

    def create() {
        respond new Pedido(params), model: [loteList: Lote.findAllByStatusLote(StatusLote.DISPONIVEL)]
    }

    @Transactional
    def save(Pedido pedido) {
        if (pedido == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (params.item?.quantidade?.class?.isArray()) {
            def itens = params.item
            for (i in 0..itens.quantidade.length - 1) {
                def itemPedido = new ItemPedido()
                itemPedido.unidade = Lote.get(params.item.unidade.id[i] as Long)
                itemPedido.quantidade = params.item.quantidade[i] as Integer
                itemPedido.bonificacao = params.item.bonificacao[i] as Integer
                itemPedido.desconto = params.item.desconto[i] as Double
                pedido.addToItensPedido(itemPedido)
            }
        } else {
            def itemPedido = new ItemPedido()
            itemPedido.unidade = Lote.get(params.item.unidade.id as Long)
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
