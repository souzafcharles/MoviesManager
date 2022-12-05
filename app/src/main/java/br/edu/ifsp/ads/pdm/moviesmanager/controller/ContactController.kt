package br.edu.ifsp.ads.pdm.moviesmanager.controller

import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Contact
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.ContactDAO
import br.edu.ifsp.ads.pdm.moviesmanager.model.database.ContactDAOSqlite
import br.edu.ifsp.ads.pdm.moviesmanager.view.MainActivity

class ContactController(private val mainActivity: MainActivity) {
    private val contactDaoImpl: ContactDAO = ContactDAOSqlite(mainActivity)

    fun insertContact(contact: Contact) = contactDaoImpl.createContact(contact)
    fun getContact(id: Int) = contactDaoImpl.retrieveContact(id)
    fun getContacts() {
        Thread {
            val returnedList = contactDaoImpl.retrieveContacts()
            mainActivity.updateContactList(returnedList)
        }.start()
    }
    fun editContact(contact: Contact) = contactDaoImpl.updateContact(contact)
    fun removeContact(id: Int) = contactDaoImpl.deleteContact(id)
}