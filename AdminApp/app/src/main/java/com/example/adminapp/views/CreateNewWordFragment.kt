package com.example.adminapp.views

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.adminapp.R
import com.example.adminapp.databinding.FragmentCreateNewWordBinding
import com.example.adminapp.model.services.WordService
import com.example.adminapp.viewModels.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNewWordFragment : Fragment() {
    private lateinit var binding:FragmentCreateNewWordBinding
    private val viewModel: ViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentCreateNewWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.token.observe(viewLifecycleOwner) { token ->
        }

        sendToServerButtonListener(viewModel.token.value.toString())

        /**
         * кнопка возврата на список
         */
        binding.backToList.setOnClickListener {
            findNavController().navigate(R.id.action_createNewWordFragment_to_WordsListFragment)
        }
    }
    fun sendToServerButtonListener(token:String){

        /**
         * кнопка отправки Word на сервер
         */
        binding.sendToServer.setOnClickListener {

            binding.textMessage2.visibility=View.INVISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                val map=WordService().create(
                    token,
                    binding.foreignWord.text.toString(),
                    binding.transcription.text.toString(),
                    binding.translation.text.toString(),
                    binding.groupWord.text.toString()
                )
                requireActivity().runOnUiThread{
                    if (map.containsKey("positive_response")){
                        binding.textMessage2.text= map["positive_response"]

                        binding.textMessage2.setTextColor(Color.GREEN)
                        binding.textMessage2.visibility=View.VISIBLE

                        binding.foreignWord.text=null
                        binding.transcription.text=null
                        binding.translation.text=null
                    } else if (map.containsKey("error_message")){
                        binding.textMessage2.text=map.get("error_message")
                        binding.textMessage2.setTextColor(Color.RED)
                        binding.textMessage2.visibility=View.VISIBLE
                    }else if (map.containsKey("connection_error")){
                        binding.textMessage2.text=map.get("connection_error")
                        binding.textMessage2.visibility=View.VISIBLE
                        binding.textMessage2.setTextColor(Color.YELLOW)
                    }
                }
            }
        }
    }
}