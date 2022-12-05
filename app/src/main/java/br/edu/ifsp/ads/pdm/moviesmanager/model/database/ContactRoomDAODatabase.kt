package br.edu.ifsp.ads.pdm.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.ContactRoomDAO
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactRoomDAODatabase: RoomDatabase() {
    abstract fun getContactRoomDao(): ContactRoomDAO
}