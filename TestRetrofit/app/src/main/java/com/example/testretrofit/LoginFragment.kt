package com.example.testretrofit

import android.os.Bundle
import android.service.autofill.VisibilitySetterAction
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testretrofit.databinding.FragmentLoginBinding
import com.example.testretrofit.models.AuthRequest
import com.example.testretrofit.retrofit.MainAPi
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel:LoginViewModel by activityViewModels()
    private lateinit var mainApi:MainAPi


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        binding.buttonSignIn.setOnClickListener {
            auth(
                AuthRequest(
                binding.login.text.toString(),
                binding.password.text.toString()
            )
            )
        }

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_ProductsFragment)
        }
    }
    private fun initRetrofit(){
        val interceptor= HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY//он будет выводить все в панель Log

        val client= OkHttpClient.Builder().addInterceptor(interceptor).build()
        /////////////////////////////////////

        //инициализируем ретрофит
        val retrofit= Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        //инициализируем интерфейс MainAPi
        mainApi=retrofit.create(MainAPi::class.java)//сделали из интерфейса класс
    }
    private fun auth(authRequest:AuthRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val response=mainApi.auth(authRequest)
            val message= response.errorBody()?.string()?.let { JSONObject(it).getString("message") }
            if (message!=null){
                requireActivity().runOnUiThread {
                    binding.errorText.text=message
                    binding.errorText.visibility=View.VISIBLE
                }
            }
            val user=response.body()
            if(user!=null){
                requireActivity().runOnUiThread {
                    Picasso.get().load(user.image).into(binding.imageView)
                    binding.textName.text=user.firstName
                    binding.buttonNext.visibility=View.VISIBLE
                    viewModel.token.value=user.token
                }
            }
        }
    }
}