package com.example.estudio_w.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.estudio_w.databinding.ActivityAgendamentoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class Agendamento : AppCompatActivity() {

    private lateinit var binding: ActivityAgendamentoBinding
    private val calendar : Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val nome = intent.extras?.getString("nome").toString()
        binding.btagendamento.text = "Agendamento para $nome"

        val datePicker = binding.datePicker
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            var dia = dayOfMonth.toString()
            val mes: String

            if (dayOfMonth <10){

              dia = "0$dayOfMonth"
            }

            if (monthOfYear <10){
                mes = "" + (monthOfYear+1)
            }else{
                mes = (monthOfYear).toString()
            }
            data = "$dia / $mes / $year"
        }
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minuto: String

            if (minute < 10){
                minuto = "0$minute"
            }
            else{
                minuto = minute.toString()
            }

            hora = "$hourOfDay:$minuto"
        }
        binding.timePicker.setIs24HourView(true)
        binding.btagendamento.setOnClickListener {
            val barbeiro1 = binding.barbeiro1
            val barbeiro2 = binding.barbeiro2

            when{
                hora.isEmpty() -> {
                    mensagem(it,"Preencha um horário","FF0000")
                }
                hora < "8:00" && hora > "19:00" ->{
                    mensagem(it,"Barbearia encontra-se fechada","FF0000")
                }
                data.isEmpty() -> {
                    mensagem(it,"Escolha uma data!","FF0000")
                }
                barbeiro1.isChecked && data.isNotEmpty() && hora.isNotEmpty() ->{
                    salvaAgendamento(it,nome, "Waldir Santiago",data,hora)
                }
                barbeiro2.isChecked && data.isNotEmpty() && hora.isNotEmpty() ->{
                    salvaAgendamento(it,nome, "Manoel",data,hora)
                }
                else ->{
                    mensagem(it,"Escolha um barbeiro!","FF0000")
                }
            }
        }
    }
    private fun mensagem(view: View, mensagem:String, cor: String){
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun salvaAgendamento(view: View, cliente: String, barbeiro:String,data:String, hora:String){

        val db = FirebaseFirestore.getInstance()

        val dadosUsuario = hashMapOf(
            "cliente" to cliente,
            "barbeiro" to barbeiro,
            "data" to data,
            "hora" to hora
        )

        db.collection("Agendamento").document(cliente).set(dadosUsuario).addOnCompleteListener {
            mensagem(view, "Agendamento Realizado com sucesso", "FF03DAC5")
        }.addOnFailureListener {
            mensagem(view,"Erro no servidor","FF0000")
        }

    }
}