package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.bases.BaseActivity
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ActivityMainBinding
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.singleton.ContatoSingleton

class MainActivity : BaseActivity() {
    // Criar uma nova lista filtrada
    val filteredList: MutableList<ContatosVO> = ContatoSingleton.lista.toMutableList()

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
        // Criar o adaptador com a lista filtrada
        adapter = ContatoAdapter(this, ContatoSingleton.lista) {onClickItemRecyclerView(it)}
        binding.recyclerView.adapter = adapter


        binding.fab.setOnClickListener {
            onClickAdd()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.search -> {

                    // Handle search icon press
                    true
                }

                else -> false
            }
        }
        // Habilitar o menu de opções


         fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.top_app_bar, menu)

            val searchItem = menu.findItem(R.id.search)
            val searchView = searchItem.actionView as SearchView

            searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    // Aqui você pode executar alguma ação quando o campo de pesquisa é expandido
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    // Aqui você pode executar alguma ação quando o campo de pesquisa é recolhido
                    adapter?.filter("") // Limpar o filtro ao recolher o campo de pesquisa
                    return true
                }
            })

            // Aqui você pode definir um ouvinte de consulta para receber os termos de pesquisa digitados pelo usuário
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // Aqui você pode executar uma ação quando o usuário enviar a consulta de pesquisa
                    var listaFiltrada: List<ContatosVO> = ContatoSingleton.lista
                    if (!query.isNullOrEmpty()) {
                        listaFiltrada = ContatoSingleton.lista.filter { contato ->
                            contato.nome.toLowerCase().contains(query.toLowerCase())
                        }
                    }
                    adapter = ContatoAdapter(this@MainActivity, listaFiltrada as MutableList<ContatosVO>) { onClickItemRecyclerView(it) }
                    binding.recyclerView.adapter = adapter
                    Toast.makeText(this@MainActivity, "Buscando por $query", Toast.LENGTH_SHORT).show()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // Aqui você pode executar uma ação toda vez que o texto da consulta de pesquisa for alterado
                    // adapter?.filter(newText) // Chamar o filtro ao alterar o texto de pesquisa
                    onClickBuscar(newText)
                    return true
                }
            })

            return true
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
   fun setupAppBar(){
       fun onCreateOptionsMenu(menu: Menu): Boolean {
           menuInflater.inflate(R.menu.top_app_bar, menu)

           val searchItem = menu.findItem(R.id.search)
           val searchView = searchItem.actionView as SearchView

           searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
               override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                   // Aqui você pode executar alguma ação quando o campo de pesquisa é expandido
                   return true
               }

               override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                   // Aqui você pode executar alguma ação quando o campo de pesquisa é recolhido
                   adapter?.filter("") // Limpar o filtro ao recolher o campo de pesquisa
                   return true
               }
           })

           // Aqui você pode definir um ouvinte de consulta para receber os termos de pesquisa digitados pelo usuário
           searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(query: String): Boolean {
                   // Aqui você pode executar uma ação quando o usuário enviar a consulta de pesquisa
                   var listaFiltrada: List<ContatosVO> = ContatoSingleton.lista
                   if(!query.isNullOrEmpty()){
                       listaFiltrada = ContatoSingleton.lista.filter { contato ->
                           if (contato.nome.toLowerCase().contains(query.toLowerCase())){
                               return@filter true
                           }
                           return@filter false
                       }
                   }
                   adapter = ContatoAdapter(this@MainActivity, listaFiltrada as MutableList<ContatosVO>) {onClickItemRecyclerView(it)}
                   binding.recyclerView.adapter = adapter
                   Toast.makeText(this@MainActivity,"Buscando por $query",Toast.LENGTH_SHORT).show()
                   return true
               }

               override fun onQueryTextChange(newText: String): Boolean {
                   // Aqui você pode executar uma ação toda vez que o texto da consulta de pesquisa for alterado
                //   adapter?.filter(newText) // Chamar o filtro ao alterar o texto de pesquisa
                   onClickBuscar(newText)
                   return true
               }
           })

           return true
       }
    }
    private fun onClickBuscar(query: String){
        var listaFiltrada: List<ContatosVO> = ContatoSingleton.lista
        if(!query.isNullOrEmpty()){
            listaFiltrada = ContatoSingleton.lista.filter { contato ->
                if (contato.nome.toLowerCase().contains(query.toLowerCase())){
                    return@filter true
                }
                return@filter false
            }
        }
        adapter = ContatoAdapter(this, listaFiltrada as MutableList<ContatosVO>) {onClickItemRecyclerView(it)}
       binding.recyclerView.adapter = adapter
        Toast.makeText(this,"Buscando por $query",Toast.LENGTH_SHORT).show()
    }

}
