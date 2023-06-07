package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.viewmodel

import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.application.ContatoApplication
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.model.ContatosVO
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.repository.ListaDeContatosRepository

class ListaDeContatosViewModel(var repository: ListaDeContatosRepository? = null) {
    fun getListaDeContatos(
        busca: String,
        onSucess: ((List<ContatosVO>) -> Unit),
        onError: ((Exception) -> Unit)

    ) {
        Thread(Runnable {
            Thread.sleep(1500)
            repository?.requestListContact(busca,
                onSucess = { list ->
                    onSucess(list)
                },
                onError = { ex ->
                    onError(ex)
                })
            var listaFiltrada: List<ContatosVO> = mutableListOf()
            try {
                listaFiltrada =
                    ContatoApplication.instance.helperDB?.buscarContatos(busca) ?: mutableListOf()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            onSucess(listaFiltrada)
        }).start()
    }
}