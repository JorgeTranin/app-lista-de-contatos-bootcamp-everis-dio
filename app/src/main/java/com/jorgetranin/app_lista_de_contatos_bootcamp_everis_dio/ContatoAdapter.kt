package com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jorgetranin.app_lista_de_contatos_bootcamp_everis_dio.databinding.ItemContatoBinding

class ContatoAdapter(
    private val context: Context,
    private var lista: List<ContatosVO>,
    private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val binding = ItemContatoBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(binding)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato = lista[position]
        with(holder.binding) {
            tvNome.text = contato.nome
            tvTelefone.text = contato.telefone
            llItem.setOnClickListener { onClick(contato.id) }
        }
    }

    class ContatoViewHolder(val binding: ItemContatoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Acesso aos elementos do XML usando a propriedade 'binding'
    }

}