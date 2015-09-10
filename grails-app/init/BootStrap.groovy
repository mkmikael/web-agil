import web.agil.*
import web.agil.security.*
import web.agil.enums.*

class BootStrap {

    def init = { servletContext ->
    	fixtures()
    }
    def destroy = {
    }

    def fixtures = {
        log.info "Create user `admin`"
    	def role = new Role(id: 1, authority: "MASTER").save(insert: true, flush: true)
    	def user = new User(id: 1, username: "admin", password: "admin").save(insert: true, flush: true)
    	UserRole.create(user, role, true)

        new Prazo(id: 1, parcela: 1, periodicidade: "AVISTA").save(insert: true, flush: true)
        new Prazo(id: 2, parcela: 1, periodicidade: "CHEQUE").save(insert: true, flush: true)
        new Prazo(id: 3, parcela: 2, periodicidade: "7-14").save(insert: true, flush: true)
        new Prazo(id: 4, parcela: 3, periodicidade: "7-14-21").save(insert: true, flush: true)
    }
}
