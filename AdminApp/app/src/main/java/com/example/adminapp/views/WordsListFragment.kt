package com.example.adminapp.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adminapp.R
import com.example.adminapp.databinding.FragmentWordsListBinding
import com.example.adminapp.model.adapters.WordsAdapter
import com.example.adminapp.model.models.Word
import com.example.adminapp.model.services.URL
import com.example.adminapp.model.services.WordService
import com.example.adminapp.viewModels.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable
import java.net.SocketTimeoutException

class WordsListFragment : Fragment() {

    private lateinit var binding: FragmentWordsListBinding
    private val viewModel: ViewModel by activityViewModels()
    private val adapter=WordsAdapter()
    private lateinit var exceptionHandler:CoroutineExceptionHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initCoroutineException()
        initButtons()
        binding.HostAddress.text= URL
        binding.editNewAddress.setText(URL)

        //если в viewModel.list прийдут с сервера новые данные то лайф дата обновит ресайклер
        viewModel.list.observe(viewLifecycleOwner) { list -> adapter.updateListWords(
            list,
            binding.minId.text.toString().toIntOrNull(),
            binding.maxId.text.toString().toIntOrNull()
        )}



    }
    private fun initRecyclerView(){
        binding.recyclerView.layoutManager=GridLayoutManager(requireContext(),1)
        binding.recyclerView.adapter=adapter
    }
    private fun initCoroutineException(){
        exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread(){
                if(throwable is SocketTimeoutException){
                    Toast.makeText(requireContext(),"потеря соединения с сервером",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(requireContext(),throwable.javaClass.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun initButtons(){
        binding.buttonUpdateList.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch(exceptionHandler){

                val list: ArrayList<Word>

                list=WordService().getAllWords()

                requireActivity().runOnUiThread{
                    viewModel.list.value=list
                }
            }

    }


        binding.buttonCreateWord.setOnClickListener {
            findNavController().navigate(R.id.action_WordsListFragment_to_createNewWordFragment)
        }

        binding.buttonFiltr.setOnClickListener {
            viewModel.list.value?.let { it1 ->
                adapter.updateListWords(
                    it1,
                    binding.minId.text.toString().toIntOrNull(),
                    binding.maxId.text.toString().toIntOrNull())
            }
        }

        binding.ChengeAddress.setOnClickListener {
            URL=binding.editNewAddress.text.toString()
            binding.HostAddress.text= URL
        }
    }
}