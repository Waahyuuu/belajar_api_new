package com.example.belajar_api

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.belajar_api.api.ApiClient
import com.example.belajar_api.databinding.ActivityCreateCatatanBinding
import com.example.belajar_api.entity.Catatan
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class CreateCatatan : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCatatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEvents()
    }

    fun setupEvents() {
        binding.buttonSave.setOnClickListener {
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            if (title.isEmpty() || content.isEmpty()) {
                displayMessage("Title dan content harus diisi")
                return@setOnClickListener
            }

            val payload = Catatan(
                id = null,
                title = title,
                content = content,
                user_id = 1
            )

            lifecycleScope.launch {
                val response = ApiClient.catatanRepository.createCatatan(payload)
                if (response.isSuccessful){
                    displayMessage("Catatan berhasil dibuat")

                    val intent = Intent(this@CreateCatatan, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                } else {
                    displayMessage("Gagal : ${response.message()}")
                }
            }
        }
    }

    fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}