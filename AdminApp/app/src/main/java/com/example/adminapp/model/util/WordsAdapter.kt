package com.example.adminapp.model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminapp.R
import com.example.adminapp.databinding.WordViewBinding
import com.example.adminapp.model.models.Word

class WordsAdapter: RecyclerView.Adapter<WordViewHolder>() {

    var wordsList= emptyList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.word_view,parent,false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordsList[position])
    }

    fun updateListWords(list:List<Word>,min:Int?,max:Int?){

        if (list==null){
            this.wordsList= emptyList()
        }else{
            this.wordsList=list.filter { word ->
                if (min!=null){
                    return@filter word.id!!>=min
                }else return@filter true
            }.filter { word ->
                if (max != null) {
                    return@filter word.id!!<=max
                }else return@filter true
            }.sortedBy{ it.id }
        }

        notifyDataSetChanged()
    }
}
class WordViewHolder(view: View/*фрагментик списка*/):RecyclerView.ViewHolder(view){
    val binding=WordViewBinding.bind(view)
    fun bind(word:Word){
        binding.foreignWord2.text=word.foreignWord
//        binding.transcription2.text=word.transcription //убрал элемент из формы
        binding.translation2.text=word.translation
        binding.id.text=word.id.toString()
        binding.groupWord2.text=word.groupWord
    }
}