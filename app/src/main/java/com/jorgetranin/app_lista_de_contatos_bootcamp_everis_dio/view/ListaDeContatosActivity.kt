package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.bases.BaseActivity
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityListaDeContatosBinding
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.helper.HelperDB
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.repository.ListaDeContatosRepository
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.view.adapter.ContatoAdapter
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.viewmodel.ListaDeContatosViewModel

class ListaDeContatosActivity : BaseActivity() {
    // Criar uma nova lista filtrada

    private var adapter: ContatoAdapter? = null

    var viewModel: ListaDeContatosViewModel? = null
    private lateinit var binding: ActivityListaDeContatosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaDeContatosBinding.inflate(LayoutInflater.from(this))

        viewModel = ListaDeContatosViewModel(ListaDeContatosRepository(HelperDB(this)))
        setContentView(binding.root)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks() {
        binding.fab.setOnClickListener { onClickAdd() }
        binding.ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd() {
        val intent = Intent(this, AddNewContato::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int) {
        val intent = Intent(this, AddNewContato::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar() {
        val busca = binding.etBuscar.text.toString()
        binding.pbLoad.visibility = View.VISIBLE
        viewModel?.getListaDeContatos(
            busca,
            onSucess = { listaFiltrada ->
                runOnUiThread {
                    adapter = ContatoAdapter(this, listaFiltrada) { onClickItemRecyclerView(it) }
                    binding.recyclerView.adapter = adapter
                    Toast.makeText(this, "Buscando por $busca", Toast.LENGTH_SHORT).show()
                    binding.pbLoad.visibility = View.GONE
                }
            },
            onError = { ex ->
                runOnUiThread {
                    AlertDialog.Builder(this).setTitle("Atenção").setMessage("Erro ao buscar")
                        .setPositiveButton("Ok"){alert, i ->
                            alert.dismiss()
                        }.show()
                }
            })

    }

}
