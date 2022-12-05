package br.edu.ifsp.ads.pdm.moviesmanager.controller

import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Contact
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.MovieDAO
import br.edu.ifsp.ads.pdm.moviesmanager.model.database.MovieSqlite
import br.edu.ifsp.ads.pdm.moviesmanager.view.MainActivity

class ContactController(private val mainActivity: MainActivity) {
    private val movieDaoImpl: MovieDAO = MovieSqlite(mainActivity)

    fun insertContact(contact: Contact) = movieDaoImpl.createContact(contact)
    fun getContact(id: Int) = movieDaoImpl.retrieveContact(id)
    fun getContacts() {
        Thread {
            val returnedList = movieDaoImpl.retrieveContacts()
            mainActivity.updateContactList(returnedList)
        }.start()
    }
    fun editContact(contact: Contact) = movieDaoImpl.updateContact(contact)
    fun removeContact(id: Int) = movieDaoImpl.deleteContact(id)
}