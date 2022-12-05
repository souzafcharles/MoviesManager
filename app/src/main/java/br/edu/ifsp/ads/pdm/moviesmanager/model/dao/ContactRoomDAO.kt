package br.edu.ifsp.ads.pdm.moviesmanager.model.dao

import androidx.room.*
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Contact

@Dao
interface ContactRoomDAO {
    companion object Constant {
        const val CONTACT_DATABASE_FILE = "contacts_room"
        const val CONTACT_TABLE = "contact"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
    }

    @Insert
    fun createContact(contact: Contact)

    @Query("SELECT * FROM $CONTACT_TABLE WHERE $ID_COLUMN = :id")
    fun retrieveContact(id: Int): Contact?

    @Query("SELECT * FROM $CONTACT_TABLE ORDER BY $NAME_COLUMN")
    fun retrieveContacts(): MutableList<Contact>

    @Update
    fun updateContact(contact: Contact): Int

    @Delete
    fun deleteContact(contact: Contact): Int
}