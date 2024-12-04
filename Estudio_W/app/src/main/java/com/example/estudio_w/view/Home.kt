package com.example.estudio_w.view


import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estudio_w.R
import com.example.estudio_w.adapter.ServicosAdapter
import com.example.estudio_w.databinding.ActivityHomeBinding
import com.example.estudio_w.model.Servico
import com.example.estudio_w.view.Agendamento


class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servico> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome")

        binding.txtnome.text = "Bem-Vindo, $nome"
        val recyclerViewServicos = binding.recyclerViewServicos
        recyclerViewServicos.layoutManager = GridLayoutManager(this, 2)
        servicosAdapter = ServicosAdapter(this, listaServicos)
        recyclerViewServicos.setHasFixedSize(true)
        recyclerViewServicos.adapter = servicosAdapter
        getServicos()

        binding.btagendar.setOnClickListener {
            val intent = Intent(this,Agendamento::class.java)
            intent.putExtra("nome",nome)
            startActivity(intent)
        }
    }

    private fun getServicos(){

        val servico1 = Servico(R.drawable.img1, "Degradê")
        listaServicos.add(servico1)

        val servico2 = Servico(R.drawable.ima2, "Cabelo + Barba")
        listaServicos.add(servico2)

        val servico3 = Servico(R.drawable.img3, "Social")
        listaServicos.add(servico3)

        val servico4 = Servico(R.drawable.img4, "Barba")
        listaServicos.add(servico4)
    }
}
