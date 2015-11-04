package web.agil

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import web.agil.enums.*
import web.agil.util.Util

@Transactional(readOnly = true)
class ClienteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", create: ["POST", "GET"]]

    @Secured(['permitAll'])
    def listJson() {
        def clientes = []
        Cliente.list().each { c ->
            if (c.participante) {
                def cliente = [:]
                cliente.id = c.id
                cliente.codigo = c.codigo
                cliente.endereco = c.participante?.endereco
                cliente.bairro = c.participante?.bairro
                cliente.referencia = c.participante?.referencia
                cliente.cidade = c.participante?.cidade
                cliente.limite = c.limite
                cliente.telefone = c.participante?.telefone
                cliente.responsavel = c.participante?.contato
                if (c.participante?.class == Organizacao) {
                    cliente.cnpj = c.participante?.cnpj
                    cliente.nomeFantasia = c.participante?.nomeFantasia
                    cliente.razaoSocial = c.participante?.razaoSocial
                    cliente.inscricaoEstadual = c.participante?.inscricaoEstadual
                } else if (c.participante?.class == Pessoa) {
                    cliente.cnpj = c.participante?.cpf
                    cliente.nomeFantasia = c.participante?.nome
                    cliente.razaoSocial = c.participante?.nome
                }
                clientes << cliente
            } // end if
        }
        render clientes as JSON
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 30, 100)
        params.tipoPessoa = params.tipoPessoa ?: 'PF'
        def criteria = {
            participante {
                if (params.tipoPessoa == 'PF') {
                    eq('class', Pessoa.class.getName())
                } else if (params.tipoPessoa == 'PJ') {
                    eq('class', Organizacao.class.getName())
                }
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
                    ilike('cpf', "%${ Util.onlyNumber( params.search_cpf ) }%")
                if (params.search_cnpj)
                    ilike('cnpj', "%${ Util.onlyNumber( params.search_cnpj ) }%")
            } // participante
        } // criteria
        def clienteList = Cliente.createCriteria().list(params, criteria)
        def clienteCount = Cliente.createCriteria().count(criteria)
        respond clienteList, model:[clienteCount: clienteCount] + params
    }

    def show(Cliente cliente) {
        respond cliente
    }

    def create() {
        def cliente = new Cliente(params)
        def participante
        def participanteType
        if (params.cpf) {
            participanteType = "PF"
            participante = Pessoa.findByCpf( Util.onlyNumber( params.cpf ) )
            if (!participante)
                participante = new Pessoa(params)
        } else if (params.cnpj) {
            participanteType = "PJ"
            participante = Organizacao.findByCnpj( Util.onlyNumber( params.cnpj ) )
            if (!participante)
                participante = new Organizacao(params)
        }
        if (participante.isAttached() && Cliente.findByParticipante(participante)) {
            flash.message = "O Cliente ja esta cadastrado no sistema!"
            redirect(action: 'choose')
            return
        }
        cliente.participante = participante
        respond cliente, model: [participanteType: participanteType,situacaoList: Situacao.values()*.name(), semanaList: Semana.values(), vendedorList: Vendedor.list()]
    }

    @Transactional
    def save(Cliente cliente) {
        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (params.participante.cpf) {
            cliente.participante = new Pessoa(params.participante)
        } else if (params.participante.cnpj) {
            cliente.participante = new Organizacao(params.participante)
        }
        cliente.codigo = Util.generateCodigo(9, Participante.count() + 1)

        cliente.participante.save()

        if (cliente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cliente.errors, view:'create'
            return
        }

        cliente.save()

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect cliente
            }
            '*' { respond cliente, [status: CREATED] }
        }
    }

    def edit(Cliente cliente) {
        respond cliente, model: [situacaoList: Situacao.values(), semanaList: Semana.values(), vendedorList: Vendedor.list()]
    }

    @Transactional
    def update(Cliente cliente) {
        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (params.participante.cpf) {
            cliente.participante = Pessoa.get(params.participante.id)
            cliente.participante.properties = params.participante
        } else if (params.participante.cnpj) {
            cliente.participante = Organizacao.get(params.participante.id)
            cliente.participante.properties = params.participante
        }

        if (cliente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cliente.errors, view:'edit'
            return
        }

        cliente.save()

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect cliente
            }
            '*'{ respond cliente, [status: OK] }
        }
    }

    @Transactional
    def delete(Cliente cliente) {

        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        cliente.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def choose() { }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
