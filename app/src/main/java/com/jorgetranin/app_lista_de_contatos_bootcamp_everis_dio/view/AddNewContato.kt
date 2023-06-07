package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.application.ContatoApplication
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.bases.BaseActivity
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityAddNewContatosBinding
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.model.ContatosVO

class AddNewContato : BaseActivity() {
    private lateinit var binding: ActivityAddNewContatosBinding
    private var idContato: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewContatosBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setupToolBar(toolBar = Toolbar(this), "Contato", true)

        setupContato()
       binding.btnSalvarConato.setOnClickListener {

           onClickSalvarContato()
       }
    }
    private fun setupContato(){
        idContato = intent.getIntExtra("index",-1)
        if (idContato == -1){
            return
        }

        Thread(Runnable {

            Thread.sleep(1500)
            //binding.pbLoadAddContato.visibility = View.VISIBLE
            val lista = ContatoApplication.instance.helperDB?.buscarContatos("$idContato",true) ?: return@Runnable
            val contato = lista.getOrNull(0) ?: return@Runnable
            runOnUiThread {
                binding.etNome.setText(contato.nome)
                binding.etTelefone.setText(contato.telefone)
                binding.pbLoadAddContato.visibility = View.GONE
                binding.btnExcluirContato.visibility = View.VISIBLE
            }
        }).start()
    }

    private fun onClickSalvarContato(){
        val nome = binding.etNome.text.toString()
        val telefone = binding.etTelefone.text.toString()
        val contato = ContatosVO(
            idContato,
            nome,
            telefone
        )
        Thread(Runnable {
            Thread.sleep(1500)
            if(idContato == -1) {
                ContatoApplication.instance.helperDB?.salvarContato(contato)
            }else{
                ContatoApplication.instance.helperDB?.updateContato(contato)
            }
            runOnUiThread {
                finish()
            }
        }).start()
    }

    fun onClickExcluirContato() {
        if(idContato > -1){
            Thread(Runnable {
                Thread.sleep(1500)
                ContatoApplication.instance.helperDB?.deletarCoontato(idContato)
                runOnUiThread {
                    finish()
                }
            }).start()
        }
    }
}