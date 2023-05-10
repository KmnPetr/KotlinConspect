package com.example.testretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testretrofit.adapter.ProductAdapter
import com.example.testretrofit.databinding.ActivityMainBinding
import com.example.testretrofit.models.AuthRequest
import com.example.testretrofit.models.User
import com.example.testretrofit.retrofit.MainAPi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var adapter:ProductAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Гость"//текст тулбара по умолчанию

        binding.recyclerView.layoutManager=LinearLayoutManager(this)//делает список вертикальным
        adapter= ProductAdapter()
        binding.recyclerView.adapter=adapter

        /////////////////////////////////////
        val interceptor=HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY//он будет выводить все в панель Log

        val client=OkHttpClient.Builder().addInterceptor(interceptor).build()
        /////////////////////////////////////

        //инициализируем ретрофит
        val retrofit= Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        //инициализируем интерфейс MainAPi
        val mainApi=retrofit.create(MainAPi::class.java)//сделали из интерфейса класс

        //////////////////////запрос на токен//////////////////////
        /*var user: User?=null
        CoroutineScope(Dispatchers.IO).launch {
            user=mainApi.auth(AuthRequest("kminchelle","0lelplR"))
            runOnUiThread {
                supportActionBar?.title = user?.firstName//изменим текст тулбара
            }
        }*/
        ///////////////////////////////////////////////////////////

        //////////////запрос в сеть с поиском по слову//////////////

        /*binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {//срабатывает при нажатии на лупку
                CoroutineScope(Dispatchers.IO).launch {
                    val list= text?.let { mainApi.getSearchProducts(user?.token?:"",it) }//сама изменилась потомучто налл вернуться может
                    runOnUiThread {
                        adapter.submitList(list?.products)//сюда передают список из сети
                    }
                }
                return true
            }
            override fun onQueryTextChange(text: String?): Boolean {//срабатывает при любом изменении
                CoroutineScope(Dispatchers.IO).launch {
                    val list= text?.let { mainApi.getSearchProducts(user?.token?:"",it) }//сама изменилась потомучто налл вернуться может
                    runOnUiThread {
                        adapter.submitList(list?.products)//сюда передают список из сети
                    }
                }
                return true
            }

        })*/
        ////////////////////////////////////////////////////////////
    }
    fun search(/*запрос в сеть с поиском по слову*/){


    }

    fun getAllProductsList(/*запрос с поиском*/){
        /*CoroutineScope(Dispatchers.IO).launch {
        val products=mainApi.getAllProducts()
        runOnUiThread {
            adapter.submitList(products.products)//сюда передают список из сети
        }*/
    }
    fun auth(){
        //функция запрашивает данные юзера по паролю
        /* binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user=mainApi.auth(
                    AuthRequest(//отсылаем тело на сервер
                        binding.userName.text.toString(),
                        binding.password.text.toString()
                    )
                )
                runOnUiThread {
                    Picasso.get().load(user.image).into(binding.image)
                    binding.firsName.text=user.firstName
                    binding.lastName.text=user.lastName
                }
            }
        }*/
    }
    fun GETrequest(/*функция делает запрос в сеть и получает ответ json Product*/){

        /*val tv= findViewById<TextView>(R.id.tv)
        val button=findViewById<Button>(R.id.button)
        var numberProduct=findViewById<EditText>(R.id.numberProduct)

        //запускаем код запроса в сеть
        button.setOnClickListener {
            //далее действия идут в параллельном потоке
            CoroutineScope(Dispatchers.IO*//*второст.поток*//*).launch {
                val product=productActivity.getProductById(Integer.parseInt(numberProduct.text.toString()))
                //далее показ информации идет на остновном потоке
                runOnUiThread*//*запустит код на основном потоке с обновлением экрана*//*{
                    tv.text=product.toString()//выведем всю инфу в текстовое поле
                }
            }*/
    }
}
