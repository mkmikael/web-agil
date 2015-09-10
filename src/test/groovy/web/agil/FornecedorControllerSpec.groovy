package web.agil

import grails.test.mixin.*
import spock.lang.*

@TestFor(FornecedorController)
@Mock(Fornecedor)
class FornecedorControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.fornecedorList
            model.fornecedorCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.fornecedor!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def fornecedor = new Fornecedor()
            fornecedor.validate()
            controller.save(fornecedor)

        then:"The create view is rendered again with the correct model"
            model.fornecedor!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            fornecedor = new Fornecedor(params)

            controller.save(fornecedor)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/fornecedor/show/1'
            controller.flash.message != null
            Fornecedor.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def fornecedor = new Fornecedor(params)
            controller.show(fornecedor)

        then:"A model is populated containing the domain instance"
            model.fornecedor == fornecedor
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def fornecedor = new Fornecedor(params)
            controller.edit(fornecedor)

        then:"A model is populated containing the domain instance"
            model.fornecedor == fornecedor
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/fornecedor/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def fornecedor = new Fornecedor()
            fornecedor.validate()
            controller.update(fornecedor)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.fornecedor == fornecedor

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            fornecedor = new Fornecedor(params).save(flush: true)
            controller.update(fornecedor)

        then:"A redirect is issued to the show action"
            fornecedor != null
            response.redirectedUrl == "/fornecedor/show/$fornecedor.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/fornecedor/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def fornecedor = new Fornecedor(params).save(flush: true)

        then:"It exists"
            Fornecedor.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(fornecedor)

        then:"The instance is deleted"
            Fornecedor.count() == 0
            response.redirectedUrl == '/fornecedor/index'
            flash.message != null
    }
}
