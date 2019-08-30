package com.deadshot.android.projectneostore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @param version - whenever we change the schema we have to up the version no.
 * @param exportSchema - true by default. and saves the schema of DB to a folder. This provides us with version.
 */
@Database(entities = [Address::class], version = 1, exportSchema = false)
abstract class AddressDatabase : RoomDatabase() {

    //Dao associated with our entity
    abstract val addressDatabaseDao: AddressDatabaseDao

    /**
     * The companion object allows clients to access the methods for creating or getting the db without instantiating the class.
     */
    companion object{
        /**
         * This will help us avoid repeateadly opening connection to db which is expensive.
         * [Volatile] - makes sure that the value of instance is always up-to-date & same to all execution threads. Value of
         *      volatile is never cached & all R/W is done through the memory. i.e changes made through one thread to instance are
         *      visible to all other threads immeidately.
         */
        @Volatile
        private var INSTANCE: AddressDatabase? = null


        fun getInstance(context: Context): AddressDatabase{
            /**
             * Multiple threads can ask for DB instance at same time leaving us with 2! instead of 1.
             * using Synchronized block only 1 thread of execution at a time can enter this block of code,
             * which makes sure DB instance is initialized only once.
             */
            synchronized(this){
                var instance = INSTANCE

                //Check if DB already exists
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AddressDatabase::class.java,
                        "address_database"
                    )
                        /**
                         * Normally we have to provide Migration object with a migration strategy when we create DB.
                         * Migration means if we change the db schema e.g. by changing no. or type of column we need
                         * a way to convert existing table & data into new Schema.
                         * Migration object is a object that defines how we take an old schema & convert it into new.
                         */
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}