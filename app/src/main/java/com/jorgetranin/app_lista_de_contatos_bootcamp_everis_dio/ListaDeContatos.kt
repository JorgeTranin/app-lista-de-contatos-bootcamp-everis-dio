package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.bases.BaseActivity
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityListaDeContatosBinding
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.singleton.ContatoSingleton

class ListaDeContatos : BaseActivity() {
    private lateinit var binding: ActivityListaDeContatosBinding
    private var index: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaDeContatosBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setupToolBar(toolBar = Toolbar(this), "Contato", true)

        setupContato()
       binding.btnSalvarConato.setOnClickListener {
           onClickSalvarContato()
       }
    }
    private fun setupContato(){
        index = intent.getIntExtra("index",-1)
        if (index == -1){
          binding.btnExcluirContato.visibility = View.GONE
            return
        }else{
            binding.btnExcluirContato.setOnClickListener {
                onClickExcluirContato()
            }
            binding.btnExcluirContato.visibility = View.VISIBLE
        }
       binding.etNome.setText(ContatoSingleton.lista[index].nome)
       binding.etTelefone.setText(ContatoSingleton.lista[index].telefone)
    }

    private fun onClickSalvarContato(){
        val nome = binding.etNome.text.toString()
        val telefone = binding.etTelefone.text.toString()
        val contato = ContatosVO(
            0,
            nome,
            telefone
        )
        if(index == -1) {
            ContatoSingleton.lista.add(contato)
        }else{
            ContatoSingleton.lista.set(index,contato)
        }
        finish()
    }

    fun onClickExcluirContato() {
        if(index > -1){
            ContatoSingleton.lista.removeAt(index)
            finish()
        }
    }
}