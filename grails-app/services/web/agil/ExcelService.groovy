package web.agil

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import grails.transaction.Transactional
import web.agil.enums.*
import web.agil.util.Util

@Transactional
class ExcelService {

    String pathExcel = "/opt/publico/produtos-v4.xls"
    String pathClientes = "/opt/publico/clientes-agil-v4.xls"

    def test() {
        FileInputStream fileInputStream = new FileInputStream(pathClientes)
        Workbook workbook = new HSSFWorkbook(fileInputStream)
        Sheet sheet = workbook.getSheetAt(0)
        Row row
        for (i in 2 .. 219) {
            row = sheet.getRow(i)
            println row.getCell(1).toString()
        }
    }


    def loadClientes() {
        final ID                    = 1
        final IS_CNPJ_OR_CPF        = 2
        final CNPJ_OR_CPF           = 3
        final CLIENTE               = 4
        final INSCRICAO_ESTADUAL    = 5
        final ENDERECO              = 6
        final NUMERO                = 7
        final COMPLEMENTO           = 8
        final BAIRRO                = 9
        final CIDADE                = 10
        final UF                    = 11
        final CEP                   = 12
        final PAIS                  = 13
        final TELEFONE              = 14
        final VENDEDOR              = 15
        final CANAL                 = 16
        final NUM_ROWS = 316
        FileInputStream fileInputStream = new FileInputStream(pathClientes)
        Workbook workbook = new HSSFWorkbook(fileInputStream)
        Sheet sheet = workbook.getSheetAt(0)
        Row row
        for ( i in 2 .. NUM_ROWS ) {
            row = sheet.getRow(i)
            def participante
            if (row.getCell( IS_CNPJ_OR_CPF )?.toString() == "CNPJ") {
                participante = new Organizacao()
                participante.cnpj = Util.onlyNumber( row.getCell( CNPJ_OR_CPF )?.toString() )
                def params = row.getCell( CLIENTE )?.toString()?.split('-')
                if (params?.length == 2) {
                    participante.nomeFantasia = Util.removeSpecialCaracter(params[1].trim())
                    participante.razaoSocial = Util.removeSpecialCaracter(params[0].trim())
                } else {
                    participante.razaoSocial = Util.removeSpecialCaracter(row.getCell( CLIENTE )?.toString())
                    participante.nomeFantasia = participante.razaoSocial
                }
                participante.inscricaoEstadual = row.getCell( INSCRICAO_ESTADUAL )?.toString()
            } else {
                participante = new Pessoa()
                participante.cpf = Util.onlyNumber( row.getCell( CNPJ_OR_CPF )?.toString() )
                def params = row.getCell( CLIENTE )?.toString()?.split('-')
                if (params?.length == 2) {
                    participante.estabelecimento = Util.removeSpecialCaracter(params[1].trim())
                    participante.nome = Util.removeSpecialCaracter(params[0].trim())
                } else {
                    participante.nome = Util.removeSpecialCaracter(row.getCell( CLIENTE )?.toString())
                }
            }
            participante.endereco = row.getCell( ENDERECO )?.toString()
            participante.referencia = row.getCell( COMPLEMENTO )?.toString()
            participante.bairro = row.getCell( BAIRRO )?.toString()
            participante.cidade = row.getCell( CIDADE )?.toString()?.toUpperCase()
            participante.telefone = row.getCell( TELEFONE )?.toString()

            participante.save()

            def c = new Cliente(participante: participante)
            c.codigo = Util.generateCodigo(9, Participante.count() + 1)
            def vendedor = row.getCell( VENDEDOR )?.toString()
            if (vendedor == "001") {
                c.vendedor = Vendedor.get(1)
            } else if (vendedor == "002") {
                c.vendedor = Vendedor.get(2)
            } else if (vendedor == "003") {
                c.vendedor = Vendedor.get(3)
            }
            /*
            def semana = row.getCell(16)?.toString()
            if (semana != null && semana != "" && semana != " ") {
                c.diaDeVisita = Semana.valueOf(semana)
            }
            */
            c.save()
            println "Save $participante"
        }
        fileInputStream.close();
    }

    def corrigirCapacidade() {
        Produto.withTransaction {
            Produto.list().each { p ->
                def m = p.descricao =~ /\d*?X\d*?X?\d*?$/
                if ( m.find() ) {
                    p.unidades.each { unidade ->
                        def list = m.group(0).split('X')
                        if (unidade.tipo.descricao == "CXA") {
                            if (list.length == 2) {
                                unidade.capacidade = list[0].trim() as Integer
                            } else if (list.length == 3) {
                                unidade.capacidade = list[2].trim() as Integer
                            }
                        } else {
                            unidade.capacidade = 1
                        }
                        println "${unidade.tipo} - ${unidade.capacidade} - ${m.group(0).split('X')} - ${p.descricao}"
                    } // each lotes
                } // if
            } // end each
        } // end transaction
    }

    def loadProdutos() {
        final ID = 1
        final FORNECEDOR = 2
        final GRUPO = 3
        final PRODUTO = 4
        final NCM = 5
        FileInputStream fileInputStream = new FileInputStream(pathExcel)
        Workbook workbook = new HSSFWorkbook(fileInputStream)
        Sheet sheet = workbook.getSheetAt(0)
        Row row
        for (i in 2..158) {
            row = sheet.getRow(i)
            def produto = new Produto()
            produto.ncm = row.getCell( NCM )?.toString()
            def descricao = Util.removeSpecialCaracter(row.getCell( PRODUTO )?.toString())?.toUpperCase()
            def params = descricao.split('-')
            if (params.length == 2) {
                produto.descricao = params[1].trim()
            } else {
                produto.descricao = params[0].trim()
            }
            def fornecedor = Fornecedor.findByDescricao(row.getCell( FORNECEDOR )?.toString())
            if (!fornecedor) {
                fornecedor = new Fornecedor( descricao: row.getCell( FORNECEDOR )?.toString() ).save()
            }
            produto.fornecedor = fornecedor
            def grupo = Grupo.findByDescricao( row.getCell( GRUPO )?.toString() )
            if (!grupo) {
                grupo = new Grupo( descricao: row.getCell( GRUPO )?.toString() ).save()
            }
            produto.grupo = grupo
            produto.save()
            println "${produto.ncm} - ${produto.descricao} - ${produto.fornecedor.descricao} - ${produto.grupo.descricao}"
        }
        fileInputStream.close();
    }

    def loadUnidades() {
        final ID        = 0
        final PRODUTO   = 4
        final VALOR_MIN = 7
        final VALOR     = 6
        final TIPO      = 8
        FileInputStream fileInputStream = new FileInputStream(pathExcel)
        Workbook workbook = new HSSFWorkbook(fileInputStream)
        Sheet sheet = workbook.getSheetAt(0)
        Row row
        for (i in 1..158) {
            row = sheet.getRow(i)
            def tipo = row.getCell( TIPO )?.toString()
            def lote  = new Lote()
            lote.vencimento = new Date()
            def descricao = row.getCell( PRODUTO )?.toString()
            def params = descricao.split('-')
            if (params.length == 2) {
                descricao = params[1].trim()
            } else {
                descricao = params[0].trim()
            }
            def produtoInstance = Produto.findByDescricao( descricao )
            if (!produtoInstance) {
                println 'not found produto'
                continue
            }
            lote.produto = produtoInstance
            lote.valor = row.getCell( VALOR )?.toString() as Double
            lote.valorMinimo = row.getCell( VALOR_MIN )?.toString() as Double
            if (tipo?.trim() in ["UNI", "PT", "DP", "LT"]) {
                lote.unidade = new Unidade(tipo: TipoUnidade.get(1), capacidade: 1, produto: produtoInstance).save()
            } else if (tipo?.trim() in ["PCT", "FD", "CJ"]) {
                lote.unidade = new Unidade(tipo: TipoUnidade.get(2), capacidade: 0, produto: produtoInstance).save()
            } else {
                println 'not found produto'
                continue
            }
            lote.statusLote = StatusLote.ESGOTADO
            lote.save()
            println lote
        }
        fileInputStream.close();
    }

    def associarFornecedorAndGrupo() {
        final FORNECEDOR = 2
        final GRUPO = 3
        FileInputStream fileInputStream = new FileInputStream(pathExcel)
        Workbook workbook = new HSSFWorkbook(fileInputStream)
        Sheet sheet = workbook.getSheetAt(0)
        Row row = sheet.getRow(2)
        def fornecedor = Fornecedor.findByDescricao( row.getCell( FORNECEDOR )?.toString() )
        for ( i in 3..158 ) {
            row = sheet.getRow(i)
            def f = Fornecedor.findByDescricao( row.getCell( FORNECEDOR )?.toString() )
            if ( fornecedor != f )
                fornecedor = f
            def grupo = Grupo.findByDescricao( row.getCell( GRUPO )?.toString() )
            fornecedor.addToGrupos( grupo )
            println "$fornecedor - $grupo"
        }
        fileInputStream.close();
    }

    def load() {
        loadClientes()
        loadProdutos()
        loadUnidades()
        corrigirCapacidade()
        associarFornecedorAndGrupo()
    }
}
