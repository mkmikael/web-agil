package web.agil

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import web.agil.util.Util

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.enums.*

@Transactional(readOnly = true)
class PedidoController {

    static allowedMethods = [savePedidos: "POST", save: "POST", update: "PUT", delete: "DELETE"]

    @Transactional
    def savePedidos(String pedidos) {
        println pedidos
        def pedidosJA = new JSONArray(pedidos);
        def response = []
        for (JSONObject pedidoJO : pedidosJA) {
            def pedido = new Pedido()
            def sdf = new SimpleDateFormat("ddMMyyyHHmmss")
            pedido.dataCriacao = sdf.parse( pedidoJO.getString("dataCriacao") )
            pedido.dataFaturamento = sdf.parse( pedidoJO.getString("dataDeFaturamento") )
            pedido.dataSincronizacao = new Date()
            pedido.cliente = Cliente.get(pedidoJO.getLong("cliente"))
            pedido.prazo = Prazo.get(pedidoJO.getLong("prazo"))
            pedido.total = pedidoJO.getDouble("total")
            pedido.codigo = Util.generateCodigo(12, Pedido.count() + 1)

            JSONArray itensJA = pedidoJO.getJSONArray("itensPedido")
            for (JSONObject itemJO : itensJA) {
                def produto = Produto.get(itemJO.getLong("produto"))
                def unidade = produto.unidades.find { it.tipo.descricao == itemJO.getString("unidade") }
                def itemPedido = new ItemPedido()
                itemPedido.confirmado = false
                itemPedido.calcular = false
                itemPedido.bonificacao = itemJO.getInt("bonificacao")
                itemPedido.desconto = itemJO.getDouble("desconto")
                itemPedido.precoNegociado = itemJO.getDouble("precoNegociado")
                itemPedido.quantidade = itemJO.getInt("quantidade")
                itemPedido.total = itemJO.getDouble("total")
                itemPedido.valor = itemJO.getDouble("valor")
                itemPedido.valorMinimo = itemJO.getDouble("valorMinimo")
                itemPedido.produto = produto
                itemPedido.unidade = unidade
                pedido.addToItensPedido(itemPedido)
            }
            pedido.save()
            response << [codigo: pedido.getCodigo(), id: pedidoJO.getLong('id')]
        }
        println response
        render response as JSON
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
        params.sort = params.sort ?: "dataSincronizacao"
        params.order = params.order ?: "desc"
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
        respond pedidoList, model:[pedidoCount: pedidoCount, statusPedidoList: StatusPedido.values(), semanaList: Semana.values()] + params
    }

    def unidadesByProduto(Long id) {
        def produto = Produto.get(id)
        def list = []
        produto.unidades.each {
            def entity = [:]
            entity.id = it.id
            entity.unidade = it.tipo.descricao
            list << entity
        }
        render {
            produto.unidades.each {
                option(it.tipo.descricao, value: it.id)
            }
        }
    }

    def precoByProdutoAndUnidade(Long produto, Long unidade) {
        def produtoO = Produto.get(produto)
        def unidadeO = Unidade.get(unidade)
        def lote = Lote.findByProdutoAndUnidade(produtoO, unidadeO, [sort: 'dataCriacao', order: 'desc'])
        render lote as JSON
    }

    def show(Pedido pedido) {
        respond pedido
    }

    def create() {
        def itens = []
        Produto.list(sort: 'descricao').each {
            itens << new ItemPedido(produto: it, unidade: it.unidades[0],
                    valor: it.lote?.valor, valorMinimo: it.lote?.valorMinimo)
        }
        respond new Pedido(params), model: [itensList: itens, prazoList: Prazo.list()]
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
                itemPedido.valor = params.item.valor[i] as Double
                itemPedido.valorMinimo = params.item.valorMinimo[i] as Double
                itemPedido.produto = Produto.get(params.item.produto.id[i] as Long)
                itemPedido.unidade = Unidade.get(params.item.unidade.id[i] as Long)
                itemPedido.quantidade = params.item.quantidade[i] as Integer
                itemPedido.bonificacao = params.item.bonificacao[i] as Integer
                itemPedido.desconto = params.item.desconto[i] as Double
                pedido.addToItensPedido(itemPedido)
            }
        } else {
            def itemPedido = new ItemPedido()
            itemPedido.produto = Produto.get(params.item.produto.id as Long)
            itemPedido.valor = params.item.valor as Double
            itemPedido.valorMinimo = params.item.valorMinimo as Double
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

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pedido.label', default: 'Pedido'), pedido.id])
                redirect pedido
            }
            '*' { respond pedido, [status: CREATED] }
        }
    }

    def edit(Pedido pedido) {
        respond pedido, model: [prazoList: Prazo.list(), loteList: Lote.findAllByStatusLote(StatusLote.DISPONIVEL)]
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
