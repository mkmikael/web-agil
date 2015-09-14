package web.agil

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import grails.transaction.Transactional

import web.agil.*
import web.agil.enums.*
import web.agil.util.Util

@Transactional
class ExcelService {

	String pathExcel = "/opt/publico/sistema_venda.xls"

    def loadClientes() {
		FileInputStream fileInputStream = new FileInputStream(pathExcel)
		Workbook workbook = new HSSFWorkbook(fileInputStream)
		Sheet sheet = workbook.getSheetAt(0)
		Row row
		for (i in 2 .. 219) {
			row = sheet.getRow(i)
			def participante
			 if (row.getCell(2)?.getStringCellValue() == "CNPJ") {
				participante = new Organizacao()
				participante.id = row.getCell(1)?.getNumericCellValue() as Long
				participante.codigo = Util.generateCodigo(12, Participante.count() + 1)
				participante.cnpj = row.getCell(3)?.getStringCellValue()
				def params = row.getCell(4)?.getStringCellValue()?.split('-')
				if (params?.length == 2) {
					participante.nomeFantasia = Util.removeSpecialCaracter(params[1].trim())
					participante.razaoSocial = Util.removeSpecialCaracter(params[0].trim())
				} else {
					participante.nomeFantasia = Util.removeSpecialCaracter(row.getCell(4)?.getStringCellValue())
				}
				participante.inscricaoEstadual = row.getCell(5)?.getStringCellValue()
				participante.endereco = row.getCell(6)?.getStringCellValue()
				participante.referencia = row.getCell(8)?.getStringCellValue()
				participante.bairro = row.getCell(9)?.getStringCellValue()
				participante.cidade = row.getCell(10)?.getStringCellValue()?.toUpperCase()
				participante.telefone = row.getCell(14)?.getStringCellValue()
			} else {
				participante = new Pessoa()
				participante.id = row.getCell(1).getNumericCellValue() as Long
				participante.codigo = Util.generateCodigo(12, Participante.count() + 1)
				participante.cpf = row.getCell(3)?.getStringCellValue()
				participante.nome = Util.removeSpecialCaracter(row.getCell(4)?.getStringCellValue())
				participante.endereco = row.getCell(6)?.getStringCellValue()
				participante.referencia = row.getCell(8)?.getStringCellValue()
				participante.bairro = row.getCell(9)?.getStringCellValue()
				participante.cidade = row.getCell(10)?.getStringCellValue()?.toUpperCase()
				participante.telefone = row.getCell(14)?.getStringCellValue()
			}
			participante.save(insert: true, flush: true)
			def c = new Cliente(participante: participante)
			if (row.getCell(15)?.getStringCellValue() == "001") {
				c.vendedor = Vendedor.get(1)
			} else if (row.getCell(15)?.getStringCellValue() == "002") {
				c.vendedor = Vendedor.get(2)
			} 
			def semana = row.getCell(16)?.getStringCellValue()
			if (semana != null && semana != "" && semana != " ") {
				println semana
				c.diaDeVisita = Semana.valueOf(semana) 
			}
			c.save()
			println "Save $participante"
		}
		fileInputStream.close();
    }

    def loadProdutos() {
    	FileInputStream fileInputStream = new FileInputStream(pathExcel)
		Workbook workbook = new HSSFWorkbook(fileInputStream)
		Sheet sheet = workbook.getSheet("produto")
		Row row
		for (i in 1..134) {
			row = sheet.getRow(i)
			def produto = new Produto()
			produto.id = row.getCell(0)?.getStringCellValue() as Long
			produto.codigo = row.getCell(1)?.getStringCellValue()
			produto.descricao = Util.removeSpecialCaracter(row.getCell(2)?.getStringCellValue())?.toUpperCase()
			def fornecedor = Fornecedor.findByDescricao(row.getCell(3)?.getStringCellValue())
			if (!fornecedor) {
				fornecedor = new Fornecedor( descricao: row.getCell(3)?.getStringCellValue() ).save(flush: true)
			}
			produto.fornecedor = fornecedor
			def grupo = Grupo.findByDescricao( row.getCell(4)?.getStringCellValue() )
			if (!grupo) {
				grupo = new Grupo( descricao: row.getCell(4)?.getStringCellValue() ).save(flush: true)
			}
			produto.grupo = grupo
			produto.save(insert: true)
			println produto.properties
		}
		fileInputStream.close();
    }

    def loadUnidades() {
    	FileInputStream fileInputStream = new FileInputStream(pathExcel)
		Workbook workbook = new HSSFWorkbook(fileInputStream)
		Sheet sheet = workbook.getSheet("precos")
		Row row
		for (i in 1..181) {
			row = sheet.getRow(i)
			def tipo = row.getCell(2)?.getStringCellValue() 
			if (tipo == "DP" || tipo == "PT") 
				continue
			def unidade  = new Unidade()
			unidade.id = row.getCell(0)?.getNumericCellValue() as Long
			unidade.produto = Produto.get( row.getCell(1)?.getStringCellValue() as Long )
			unidade.tipo = row.getCell(2)?.getStringCellValue()
			unidade.valor = row.getCell(3)?.getNumericCellValue()
			unidade.valorMinimo = row.getCell(4)?.getNumericCellValue()
			if (tipo == "UNI")
				unidade.tipoUnidade = TipoUnidade.get(2)
			else if (tipo == "CXA")
				unidade.tipoUnidade = TipoUnidade.get(1)
			unidade.save(insert: true, flush: true)
			println unidade.properties
		}
		fileInputStream.close();
    }

    def associarFornecedorAndGrupo() {
    	FileInputStream fileInputStream = new FileInputStream(pathExcel)
		Workbook workbook = new HSSFWorkbook(fileInputStream)
		Sheet sheet = workbook.getSheet("produto")
		Row row = sheet.getRow(1)
		def fornecedor = Fornecedor.findByDescricao( row.getCell(3)?.getStringCellValue() )
		for ( i in 2..134 ) {
			row = sheet.getRow(i)
			def f = Fornecedor.findByDescricao( row.getCell(3)?.getStringCellValue() )
			if ( fornecedor != f )
				fornecedor = f
			def grupo = Grupo.findByDescricao( row.getCell(4)?.getStringCellValue() )
			fornecedor.addToGrupos( grupo )
			println "$fornecedor - $grupo"
		}
		fileInputStream.close();
    }

    def load() {
    	new TipoUnidade(id: 1, tipo: "CXA", capacidade: 0).save(flush: true)
    	new TipoUnidade(id: 2, tipo: "UNI", capacidade: 1).save(flush: true)
    	new Vendedor(id: 1, codigo: "001").save(flush: true)
    	new Vendedor(id: 2, codigo: "002").save(flush: true)

    	loadClientes()
    	loadProdutos()
    	loadUnidades()
    	associarFornecedorAndGrupo()
    }
}
