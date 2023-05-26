package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityListaDeContatosBinding
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        binding.fab.setOnClickListener {
            val intent = Intent(this, ListaDeContatos::class.java)
            startActivity(intent)
        }


    }
}