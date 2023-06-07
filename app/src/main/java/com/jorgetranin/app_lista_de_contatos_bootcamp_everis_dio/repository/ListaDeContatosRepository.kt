package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.repository

import android.annotation.SuppressLint
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.bases.BaseRepository
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB.Companion.COLUMNS_ID
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB.Companion.COLUMNS_NOME
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB.Companion.COLUMNS_TELEFONE
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB.Companion.TABLE_NAME
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.model.ContatosVO
import java.sql.SQLDataException

class ListaDeContatosRepository(var helperDBpar: HelperDB? = null) : BaseRepository(helperDBpar) {

    @SuppressLint("Range")
    fun requestListContact(
        busca: String,
        onSucess: ((List<ContatosVO>) -> Unit),
        onError: ((Exception) -> Unit)
    ) {
        try {


            val db = readableDatabase
            val lista = mutableListOf<ContatosVO>()
            val where: String? = null
            val args: Array<String> = arrayOf()

            val cursor = db?.query(TABLE_NAME, null, where, args, null, null, null)
            if (cursor == null) {
                db?.close()
                onError(SQLDataException("Erro na query"))
                return
            }
            while (cursor.moveToNext()) {
                val contato = ContatosVO(
                    cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_TELEFONE))
                )
                lista.add(contato)
            }
            db?.close()
            onSucess(lista)
        }catch (ex: Exception){
            onError(ex)
        }
    }
}