package com.example.roomtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.roomtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db=MainDb.getDb(this)

        db.getDAO().getAllItem().asLiveData().observe(this){ itList/*тут совпадение имен надо исправить*/ ->//получаем обновляем данные с базы. it это итератор
            binding.tvList.text=""
            itList.forEach { //в обсерве и в фориче итераторы одинаково называются надо переименовать один
                val text="Id: ${it.id} Word: ${it.word} Transcr: ${it.transcription} :Translation: ${it.translation}\n"
                binding.tvList.append(text)
            }
        }

        binding.saveButton.setOnClickListener {
            val item=Item(null,binding.nameText.text.toString(),binding.priceText.text.toString(),null)
            Thread{//работа с базой выполняется в отдельном потоке или в корутине
                db.getDAO().insertItem(item)
            }.start()
        }
    }
}