package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.application.ContatoApplication
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.bases.BaseActivity
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    // Criar uma nova lista filtrada

    private var adapter:ContatoAdapter? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
      binding.fab.setOnClickListener { onClickAdd() }
       binding.ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ListaDeContatos::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ListaDeContatos::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = binding.etBuscar.text.toString()
        Thread(Runnable {
            Thread.sleep(1500)
            var listaFiltrada: List<ContatosVO> = mutableListOf()
            try {
                listaFiltrada = ContatoApplication.instance.helperDB?.buscarContatos(busca) ?: mutableListOf()
            }catch (ex: Exception){
                ex.printStackTrace()
            }
            runOnUiThread {
                adapter = ContatoAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
                binding.recyclerView.adapter = adapter
                Toast.makeText(this,"Buscando por $busca",Toast.LENGTH_SHORT).show()
            }
        }).start()
    }

}
