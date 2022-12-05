package br.edu.ifsp.ads.pdm.moviesmanager.controller

import android.os.AsyncTask
import androidx.room.Room
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.ContactRoomDAO
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.ContactRoomDAO.Constant.CONTACT_DATABASE_FILE
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Contact
import br.edu.ifsp.ads.pdm.moviesmanager.model.database.ContactRoomDAODatabase
import br.edu.ifsp.ads.pdm.moviesmanager.view.MainActivity

class ContactRoomController(private val mainActivity: MainActivity) {
    private val contactDaoImpl: ContactRoomDAO by lazy {
        Room.databaseBuilder(
            mainActivity,
            ContactRoomDAODatabase::class.java,
            CONTACT_DATABASE_FILE
        ).build().getContactRoomDao()
    }

    fun insertContact(contact: Contact) {
        Thread {
            contactDaoImpl.createContact(contact)
            getContacts()
        }.start()
    }

    fun getContact(id: Int) = contactDaoImpl.retrieveContact(id)
    fun getContacts() {
        object: AsyncTask<Unit, Unit, MutableList<Contact>>(){
            override fun doInBackground(vararg params: Unit?): MutableList<Contact> {
                val returnList = mutableListOf<Contact>()
                returnList.addAll(contactDaoImpl.retrieveContacts())
                return returnList
            }

            override fun onPostExecute(result: MutableList<Contact>?) {
                super.onPostExecute(result)
                if (result != null){
                    mainActivity.updateContactList(result)
                }
            }
        }.execute()
    }

    fun editContact(contact: Contact) {
        Thread {
            contactDaoImpl.updateContact(contact)
            getContacts()
        }.start()
    }

    fun removeContact(contact: Contact) {
        Thread {
            contactDaoImpl.deleteContact(contact)
            getContacts()
        }.start()
    }
}