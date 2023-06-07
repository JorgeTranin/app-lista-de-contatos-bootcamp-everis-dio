package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.bases

import android.database.sqlite.SQLiteDatabase
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB

open class BaseRepository(var helperDB: HelperDB? = null) {
    val readableDatabase: SQLiteDatabase?
        get() {
            return helperDB?.readableDatabase
        }
    val writableDatabase: SQLiteDatabase?
        get() {
            return helperDB?.writableDatabase
        }
}