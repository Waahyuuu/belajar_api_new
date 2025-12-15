package com.example.belajar_api

import android.content.Intent
import android.graphics.pdf.content.PdfPageGotoLinkContent
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.belajar_api.api.ApiClient
import com.example.belajar_api.databinding.ActivityEditCatatanBinding
import com.example.belajar_api.entity.Catatan
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class EditCatatanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditCatatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_edit_catatan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEvents()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun setupEvents() {
        binding.buttonSave.setOnClickListener {
            val id = intent.getIntExtra("id_catatan",0)
            val title = binding.titleEdit.text.toString()
            val content = binding.contentEdit.text.toString()

            if (content.isEmpty()|| title.isEmpty()){
                displayMessage("title dan contentnya harus diisi")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val catatan = Catatan(id, title, content, user_id = null)
                val data = ApiClient.catatanRepository.editCatatan(id, catatan)

                if (data.isSuccessful) {
                    displayMessage("Catatan berhasil diubah")
                    switchPage(MainActivity::class.java)
                } else {
                    displayMessage("Error: ${data.message()}")
                }
            }
        }
    }

    fun loadData() {
        val id = intent.getIntExtra("id_catatan",0)
        if (id == 0) {
            displayMessage("Error: id catatan tidak terkirim")
            switchPage(MainActivity::class.java)
            return
        }

        lifecycleScope.launch {
            val data = ApiClient.catatanRepository.getCatatan(id)
            if (data.isSuccessful){
                val catatan = data.body()
                binding.titleEdit.setText(catatan?.title)
                binding.contentEdit.setText(catatan?.content)
            } else {
                displayMessage("Error: ${data.message()}")
                switchPage(MainActivity::class.java)
            }
        }
    }

    fun displayMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun switchPage(destination: Class<MainActivity>){
        val intent = Intent(this, destination)
        startActivity(intent)
        finish()
    }
}