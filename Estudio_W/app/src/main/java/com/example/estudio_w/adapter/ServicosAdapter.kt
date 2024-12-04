package com.example.estudio_w.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.estudio_w.databinding.ServicosItensBinding
import com.example.estudio_w.model.Servico


class ServicosAdapter (private val context: Context, private val listaServicos: MutableList<Servico>):
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        val itemLista=ServicosItensBinding.inflate(LayoutInflater.from(context),parent,false)
        return ServicosViewHolder(itemLista)
    }

    override fun getItemCount() = listaServicos.size

    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        holder.imgServicos.setImageResource(listaServicos[position].img!!)
        holder.txtServicos.text=listaServicos[position].nome
    }

    inner class ServicosViewHolder(binding: ServicosItensBinding):RecyclerView.ViewHolder(binding.root){
        val imgServicos = binding.imgServico
        val txtServicos = binding.txtServico

    }
}
