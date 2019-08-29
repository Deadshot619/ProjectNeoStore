package com.deadshot.android.projectneostore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Address::class], version = 1, exportSchema = false)
abstract class AddressDatabase : RoomDatabase() {
    abstract val addressDatabaseDao: AddressDatabaseDao

    companion object{
        private var INSTANCE: AddressDatabase? = null

        fun getInstance(context: Context): AddressDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AddressDatabase::class.java,
                        "address_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}