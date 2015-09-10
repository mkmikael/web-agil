package web.agil

import grails.test.mixin.*
import spock.lang.*

@TestFor(VendedorController)
@Mock(Vendedor)
class VendedorControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.vendedorList
            model.vendedorCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.vendedor!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def vendedor = new Vendedor()
            vendedor.validate()
            controller.save(vendedor)

        then:"The create view is rendered again with the correct model"
            model.vendedor!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            vendedor = new Vendedor(params)

            controller.save(vendedor)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/vendedor/show/1'
            controller.flash.message != null
            Vendedor.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def vendedor = new Vendedor(params)
            controller.show(vendedor)

        then:"A model is populated containing the domain instance"
            model.vendedor == vendedor
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def vendedor = new Vendedor(params)
            controller.edit(vendedor)

        then:"A model is populated containing the domain instance"
            model.vendedor == vendedor
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/vendedor/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def vendedor = new Vendedor()
            vendedor.validate()
            controller.update(vendedor)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.vendedor == vendedor

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            vendedor = new Vendedor(params).save(flush: true)
            controller.update(vendedor)

        then:"A redirect is issued to the show action"
            vendedor != null
            response.redirectedUrl == "/vendedor/show/$vendedor.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/vendedor/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def vendedor = new Vendedor(params).save(flush: true)

        then:"It exists"
            Vendedor.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(vendedor)

        then:"The instance is deleted"
            Vendedor.count() == 0
            response.redirectedUrl == '/vendedor/index'
            flash.message != null
    }
}
