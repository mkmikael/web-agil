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
    	def role = new Role(id: 1, authority: "ROLE_MASTER").save(insert: true, flush: true)
    	def user = new User(id: 1, username: "ADMIN", password: "admin").save(insert: true, flush: true)
    	UserRole.create(user, role, true)
    }
}
