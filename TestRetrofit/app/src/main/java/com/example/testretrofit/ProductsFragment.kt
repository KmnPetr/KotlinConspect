package com.example.testretrofit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testretrofit.adapter.ProductAdapter
import com.example.testretrofit.databinding.FragmentProductsBinding
import com.example.testretrofit.retrofit.MainAPi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel:LoginViewModel by activityViewModels()
    private lateinit var mainApi: MainAPi
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()
        initRCView()

        viewModel.token.observe(viewLifecycleOwner){ token->//следит за изменениями данных

            //////////////запрос в сеть с поиском по слову//////////////
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {//срабатывает при нажатии на лупку
                    CoroutineScope(Dispatchers.IO).launch {
                        val list= text?.let { mainApi.getSearchProducts(token, it) }//сама изменилась потомучто налл вернуться может
                        requireActivity().runOnUiThread {
                            adapter.submitList(list?.products)//сюда передают список из сети
                        }
                    }
                    return true
                }
                override fun onQueryTextChange(text: String?): Boolean {//срабатывает при любом изменении
                    CoroutineScope(Dispatchers.IO).launch {
                        val list= text?.let { mainApi.getSearchProducts(token, it) }//сама изменилась потомучто налл вернуться может
                        requireActivity().runOnUiThread {
                            adapter.submitList(list?.products)//сюда передают список из сети
                        }
                    }
                    return true
                }

            })
            ////////////////////////////////////////////////////////////


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
    private fun initRCView(){
        binding.recyclerView.layoutManager= LinearLayoutManager(context/*this раньше было в активити*/)//делает список вертикальным
        adapter= ProductAdapter()
        binding.recyclerView.adapter=adapter
    }
}