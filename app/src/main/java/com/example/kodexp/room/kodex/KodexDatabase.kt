package com.example.kodexp.room.kodex

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kodexp.utils.ioThread

@Database(entities = [Kodex::class], version = 1)
abstract class KodexDatabase : RoomDatabase() {
    abstract fun kodexDao(): KodexDao

    companion object {

        @Volatile private var INSTANCE: KodexDatabase? = null

        fun getInstance(context: Context): KodexDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                KodexDatabase::class.java, "kodex-database")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            for (kodex in PREPOPULATE_DATA) getInstance(context).kodexDao().insertAll(kodex)
                        }
                    }
                })
                .build()

        val PREPOPULATE_DATA = listOf(Kodex(1, true), Kodex(1, false))
    }
}