package com.example.adminapp.views

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminapp.databinding.ContentMainBinding
import com.example.adminapp.model.services.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSettings()
    }

    override fun onStop() {
        super.onStop()
        saveSettings()
    }

    //Загружает настройки из проперти
    private fun getSettings() {
        val settings: SharedPreferences? = this.getSharedPreferences("settings",0)

        if (settings != null) {
            if (settings.contains("URL")) URL= settings.getString("URL","http://0.0.0.0:28700").toString()
        }
    }
    //Сохраняет настройки в проперти
    private fun saveSettings(){
        val settings: SharedPreferences? = this.getSharedPreferences("settings",0)

        if (settings != null) {
            settings.edit().putString("URL",URL).apply()
        }
    }
}