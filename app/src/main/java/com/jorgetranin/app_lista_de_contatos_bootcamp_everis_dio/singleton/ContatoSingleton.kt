package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.singleton

import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.ContatosVO

object ContatoSingleton {
    var lista: MutableList<ContatosVO> = mutableListOf()
}