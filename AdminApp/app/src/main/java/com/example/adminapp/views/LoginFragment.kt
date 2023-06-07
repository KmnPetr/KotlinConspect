package com.example.adminapp.views

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.adminapp.R
import com.example.adminapp.databinding.FragmentLoginBinding
import com.example.adminapp.model.services.AuthService
import com.example.adminapp.viewModels.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel:ViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
        getSettings()

        viewModel.token.observe(viewLifecycleOwner){token->    }//пока не нужно
    }

    override fun onStop() {
        super.onStop()
        saveSettings()
    }
    //Загружает настройки из проперти
    private fun getSettings() {
        val settings: SharedPreferences? = this.getActivity()?.getSharedPreferences("settings",0)

        if (settings != null) {
            if (settings.contains("email")) binding.login.setText(settings.getString("email",""))
            if (settings.contains("password"))binding.login.setText(settings.getString("password",""))
        }
    }
    //Сохраняет настройки в проперти
    private fun saveSettings(){
        val settings: SharedPreferences? = this.getActivity()?.getSharedPreferences("settings",0)

        if (settings != null) {
            settings.edit().putString("email",binding.login.text.toString()).apply()
            settings.edit().putString("password",binding.password.text.toString()+"fy")
            Toast.makeText(context,"password нельзя хранить в проперти",Toast.LENGTH_LONG).show()
        }
    }
    //здесь код кнопок
    private fun initButtons(){
        binding.signIn.setOnClickListener {

            binding.textMassage.visibility=View.INVISIBLE

            var map:Map<String,String>
            CoroutineScope(Dispatchers.IO).launch {

                map= AuthService().authentication(
                    binding.login.text.toString(),
                    binding.password.text.toString()
                )
                requireActivity().runOnUiThread(){
                    if (map.containsKey("jwt-token")){
                        viewModel.token.value=map.get("jwt-token")

                        binding.textMassage.text="Authentication was successful"
                        binding.textMassage.visibility=View.VISIBLE
                        binding.textMassage.setTextColor(Color.GREEN)
                    }else if(map.containsKey("massage")){
                        binding.textMassage.text=map.get("massage")
                        binding.textMassage.visibility=View.VISIBLE
                        binding.textMassage.setTextColor(Color.RED)
                    }else if (map.containsKey("connection_error")){
                        binding.textMassage.text=map.get("connection_error")
                        binding.textMassage.visibility=View.VISIBLE
                        binding.textMassage.setTextColor(Color.YELLOW)
                    }
                }
            }
        }

        binding.buttonToWordsList.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_WordsListFragment)
        }
    }
}