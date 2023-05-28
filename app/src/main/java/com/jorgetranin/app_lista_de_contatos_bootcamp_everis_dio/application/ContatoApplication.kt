package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.application

import android.app.Application
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB

class ContatoApplication : Application(){

    var helperDB : HelperDB? = null
        private set
companion object{
    lateinit var instance : ContatoApplication
}
    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}
