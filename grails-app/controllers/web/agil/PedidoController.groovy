package web.agil

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
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

    @Secured(['permitAll'])
    @Transactional
    def savePedidos(String pedidos) {
        println pedidos
        def pedidosJA = new JSONArray(pedidos);
        def response = []
        for (JSONObject pedidoJO : pedidosJA) {
            def pedido = new Pedido()
            def sdf = new SimpleDateFormat("ddMMyyyyHHmmss")
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
    def changeItemPedido(Long id, Boolean checked) {
        def item = ItemPedido.get(id)
        item.confirmado = checked
        item.pedido.calcularTotalAvaliado()
        def data = [totalAvaliado: item.pedido.totalAvaliado]
        render data as JSON
    }

    @Transactional
    def negarPedidos() {
        def ids = []
        params.each { entry ->
            def matcher = entry.key =~ /^check(\d*)/
            if ( matcher.find() )
                ids << (matcher[0][1] as Long)
        }
        if (!ids.empty) {
            Pedido.getAll(ids).each { p ->
                if (p.statusPedido == StatusPedido.PENDENTE) {
                    p.statusPedido = StatusPedido.NEGADO
                } else {
                    if (!flash.message) flash.message = []
                    flash.message << "Erro no pedido ${p.codigo}, voce so pode negar um pedido que esta pendente."
                }
            }
        } else {
            flash.message = "Selecione pelo menos um pedido."
        }
        redirect(action: 'index')
    }

    @Transactional
    def confirmarPedidos() {
        def ids = []
        params.each { entry ->
            def matcher = entry.key =~ /^check(\d*)/
            if ( matcher.find() )
                ids << (matcher[0][1] as Long)
        }
        if (!ids.empty) {
            Pedido.getAll(ids).each { p ->
                if (p.statusPedido == StatusPedido.PENDENTE) {
                    p.statusPedido = StatusPedido.CONFIRMADO
                    p.itensPedido.each { item ->
                        println item.unidade.capacidade
                        item.produto.estoque -= ((item.quantidade + item.bonificacao) * item.unidade.capacidade)
                    }
                } else {
                    if (!flash.message) flash.message = []
                    flash.message << "Erro no pedido ${p.codigo}, voce so pode confirmar um pedido que esta pendente."
                }
            }
        } else {
            flash.message = "Selecione pelo menos um pedido."
        }
        redirect(action: 'index')
    }

    @Transactional
    def desfazerPedidos() {
        def ids = []
        params.each { entry ->
            def matcher = entry.key =~ /^check(\d*)/
            if ( matcher.find() )
                ids << (matcher[0][1] as Long)
        }
        if (!ids.empty) {
            Pedido.getAll(ids).each { p ->
                if (p.statusPedido == StatusPedido.CONFIRMADO) {
                    p.statusPedido = StatusPedido.PENDENTE
                    p.itensPedido.each { item -> item.produto.estoque += ((item.quantidade + item.bonificacao) * item.unidade.capacidade) }
                } else if (p.statusPedido == StatusPedido.NEGADO) {
                    p.statusPedido = StatusPedido.PENDENTE
                } else {
                    if (!flash.message) flash.message = []
                    flash.message << "Erro no pedido ${p.codigo}, voce so pode desfazer um pedido que esta negado ou confirmado."
                }
            }
        } else {
            flash.message = "Selecione pelo menos um pedido."
        }
        redirect(action: 'index')
    }

    @Transactional
    @Deprecated
    def negarPedido(Long id) {
        def p = Pedido.get(id)
        if (p.statusPedido == StatusPedido.PENDENTE) {
            p.statusPedido = StatusPedido.NEGADO
            p.itensPedido.each { item -> item.confirmado = false }
            p.calcularTotal()
            p.calcularTotalAvaliado()
        } else {
            flash.message = "Voce so pode negar um pedido que esta pendente"
        }
        redirect(action: 'show', id: id)

    }

    @Transactional
    @Deprecated
    def confirmarPedido(Long id) {
        def p = Pedido.get(id)
        if (p.statusPedido == StatusPedido.PENDENTE) {
            p.statusPedido = StatusPedido.CONFIRMADO
            p.itensPedido.each { item ->
                if (item.confirmado) {
                    item.produto.estoque -= ((item.quantidade + item.bonificacao) * item.unidade.capacidade )
                    item.produto.save()
                }
            }
            p.calcularTotal()
            p.calcularTotalAvaliado()
        } else {
            flash.message = "Voce so pode confirmar um pedido que esta pendente"
        }
        redirect(action: 'show', id: id)
    }

    @Transactional
    @Deprecated
    def desfazerPedido(Long id) {
        def p = Pedido.get(id)
        if (p.statusPedido == StatusPedido.CONFIRMADO) {
            p.statusPedido = StatusPedido.DESFEITO
            p.itensPedido.each { item ->
                if (item.confirmado) {
                    item.confirmado = false
                    item.produto.estoque += ((item.quantidade + item.bonificacao) * item.unidade.capacidade )
                    item.produto.save()
                }
            }
            p.calcularTotal()
            p.calcularTotalAvaliado()
        } else {
            flash.message = "Voce so pode desfazer um pedido que esta confirmado"
        }
        redirect(action: 'show', id: id)
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
                if (params.search_codigo)
                    ilike('codigo', "%${params.search_codigo}%")
                participante {
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
        pedido.calcularTotalAvaliado()
        respond pedido
    }

    def create() {
        def pedido = new Pedido()
        def itens = []
        def produtosId = []
        if (pedido.itensPedido)
            produtosId = pedido.itensPedido*.produto.id
        def produtList = Produto.executeQuery("""
            select distinct p from Produto p inner join fetch p.unidades order by p.descricao
        """)
        produtList.each { p ->
            if (p.id in produtosId) {
                itens << pedido.itensPedido.find { item -> item.produto.id == p.id }
            } else {
                def lote = p.lote
                itens << new ItemPedido(produto: p, unidade: p.unidades[0],
                        valor: lote?.valor, valorMinimo: lote?.valorMinimo)
            }
        }
        respond pedido, model: [prazoList: Prazo.list(), itensList: itens]
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
        def itens = []
        def produtosId = pedido.itensPedido*.produto.id
        def produtList = Produto.executeQuery("""
            select distinct p from Produto p inner join fetch p.unidades order by p.descricao
        """)
        produtList.each { p ->
            if (p.id in produtosId) {
                itens << pedido.itensPedido.find { item -> item.produto.id == p.id }
            } else
                itens << new ItemPedido(produto: p, unidade: p.unidades[0],
                        valor: p.lote?.valor, valorMinimo: p.lote?.valorMinimo)
        }
        respond pedido, model: [prazoList: Prazo.list(), itensList: itens]
    }

    @Transactional
    def update(Pedido pedido) {
        if (pedido == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pedido.itensPedido.clear()

        if (params.item?.quantidade?.class?.isArray()) {
            def itens = params.item
            for (i in 0..itens.quantidade.length - 1) {
                def itemPedido = itens?.id[i] ? ItemPedido.get(itens?.id[i] as Long) : new ItemPedido()
                itemPedido.valor = itens?.valor[i] as Double
                itemPedido.valorMinimo = itens?.valorMinimo[i] as Double
                itemPedido.produto = Produto.get(itens?.produto?.id[i] as Long)
                itemPedido.unidade = Unidade.get(itens?.unidade?.id[i] as Long)
                itemPedido.quantidade = itens?.quantidade[i] as Integer
                itemPedido.bonificacao = itens?.bonificacao[i] as Integer
                itemPedido.desconto = itens?.desconto[i] as Double
                itemPedido.calc()
                pedido.addToItensPedido(itemPedido)
            }
        } else {
            def itemPedido = params.item?.id ? ItemPedido.get(params.item?.id as Long) : new ItemPedido()
            itemPedido.produto = Produto.get(params.item?.produto?.id as Long)
            itemPedido.valor = params.item?.valor as Double
            itemPedido.valorMinimo = params.item?.valorMinimo as Double
            itemPedido.unidade = Unidade.get(params.item?.unidade?.id as Long)
            itemPedido.quantidade = params.item?.quantidade as Integer
            itemPedido.bonificacao = params.item?.bonificacao as Integer
            itemPedido.desconto = params.item?.desconto as Double
            itemPedido.calc()
            pedido.addToItensPedido(itemPedido)
        }

        pedido.calcularTotal()

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
