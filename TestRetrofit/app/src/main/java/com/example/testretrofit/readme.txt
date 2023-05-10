https://dummyjson.com/docs/products

в манифест добавить разрешение на интернет
    <uses-permission android:name="android.permission.INTERNET"/>
    если после разрешения не заработает то удалить приложение и еще раз создать

в зависимости добавить

    // добавили сам ретрофит
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    //json конвертер
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    //////////////////////////////////////////////////////////////////////
    ссылка на плейлист
    https://youtube.com/playlist?list=PLmjT2NFTgg1cHUclGx5L9c92FG4pCN6lC

    1e видео
            научились настраивать ретрофит и отправлять гет запрос
    2e видео
            подключали OkHttpClient который выводит в логи тело запроса и ответа json

            добавили зависимости
                implementation 'com.squareup.okhttp3:okhttp:4.11.0'
                implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'

    3e видео
            получали юзера по паролю

    4e видео
            получали список из ретрофита и создавали ресфйклер лист

    5eвидео
            делали запрос на поиск по части слова
            (@Query("q")name:String) вставляет параметр по типу /products/search?q=phone

    6e видео
            получали токен из юзера и теперь все запросы в сеть идут с токеном вместе
            поменяли название тулбара

    7е видео
