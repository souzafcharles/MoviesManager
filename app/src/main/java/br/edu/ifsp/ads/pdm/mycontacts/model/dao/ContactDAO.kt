package br.edu.ifsp.ads.pdm.mycontacts.model.dao

import br.edu.ifsp.ads.pdm.mycontacts.model.entity.Contact

interface ContactDAO {
    fun createContact(contact: Contact): Int
    fun retrieveContact(id: Int): Contact?
    fun retrieveContacts(): MutableList<Contact>
    fun updateContact(contact: Contact): Int
    fun deleteContact(id: Int): Int
}