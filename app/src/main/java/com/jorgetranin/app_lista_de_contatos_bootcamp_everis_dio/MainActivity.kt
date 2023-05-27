package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var adapter:ContatoAdapter? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

      binding.recyclerView.layoutManager = LinearLayoutManager(this)
        ContatoSingleton.lista.add(ContatosVO(1,"Teste 1", "000000"))
        ContatoSingleton.lista.add(ContatosVO(2,"Teste 2", "000000"))
        ContatoSingleton.lista.add(ContatosVO(3,"Teste 3", "000000"))
        adapter = ContatoAdapter(this,ContatoSingleton.lista) {onClickItemRecyclerView(it)}
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            val intent = Intent(this, ListaDeContatos::class.java)
            startActivity(intent)
        }


    }
    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
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
}