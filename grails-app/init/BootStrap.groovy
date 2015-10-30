import web.agil.*
import web.agil.security.*
import web.agil.enums.*

class BootStrap {

    def init = { servletContext ->
        println "****************************"
        println "Sistema Agil Distribuicoes"
        println "Desenv: Mikael Lima"
        println "BlackSoftware - 2015"
        println "****************************"
    	fixtures()
    }
    def destroy = {
    }

    def fixtures() {
        println "INIT FIXTURES"
        if (User.count() == 0) {
            println "LOAD SECURITY"
            def role = new Role(id: 1, authority: "ROLE_MASTER").save(insert: true, flush: true)
            def user = new User(id: 1, username: "ADMIN", password: "admin").save(insert: true, flush: true)
            UserRole.create(user, role, true)
        }
        if (Tributo.count() == 0) {
            println "LOAD TRIBUTOS"
            new Tributo(id: 1, descricao: 'ICMS').save(insert: true, flush: true)
            new Tributo(id: 2, descricao: 'COFINS').save(insert: true, flush: true)
            new Tributo(id: 3, descricao: 'PIS').save(insert: true, flush: true)
        }
        if (TipoUnidade.count() == 0) {
            println "LOAD TIPOUNIDADES"
            new TipoUnidade(id: 1, descricao: "UNI").save(insert: true, flush: true)
            new TipoUnidade(id: 2, descricao: "CXA").save(insert: true, flush: true)
        }
        if (Prazo.count() == 0) {
            println "LOAD PRAZOS"
            new Prazo(id: 1, parcela: 0, periodicidade: "AVISTA", ativo: true ).save(insert: true, flush: true)
            new Prazo(id: 2, parcela: 1, periodicidade: "5", ativo: true ).save(insert: true, flush: true)
            new Prazo(id: 3, parcela: 1, periodicidade: "10", ativo: true ).save(insert: true, flush: true)
            new Prazo(id: 4, parcela: 1, periodicidade: "15", ativo: true ).save(insert: true, flush: true)
            new Prazo(id: 5, parcela: 2, periodicidade: "5 - 10", ativo: true ).save(insert: true, flush: true)
            new Prazo(id: 6, parcela: 2, periodicidade: "5 - 15", ativo: true ).save(insert: true, flush: true)
            new Prazo(id: 7, parcela: 2, periodicidade: "10 - 15", ativo: true ).save(insert: true, flush: true)
            new Prazo(id: 8, parcela: 3, periodicidade: "5 - 10 - 15", ativo: true ).save(insert: true, flush: true)
        }
        if (Vendedor.count() == 0) {
            println "LOAD VENDEDORES"
            new Vendedor(id: 1, codigo: "001").save(insert: true, flush: true)
            new Vendedor(id: 2, codigo: "002").save(insert: true, flush: true)
            new Vendedor(id: 3, codigo: "003").save(insert: true, flush: true)
        }
        println "END FIXTURES"
    }
}
