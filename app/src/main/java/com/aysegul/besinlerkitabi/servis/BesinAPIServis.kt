package com.aysegul.besinlerkitabi.servis

import com.aysegul.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIServis {
    //GET-POST

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //BASE-URL -> https://raw.githubusercontent.com/
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    private val BASE_URL ="https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()   //Retrofit objesini oluşturduk
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  //bir json formatını modele çevirmek için
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //RxJava Kullanacağımız için belirtmemiz gerekiyor
            .build()
            .create(BesinAPI::class.java)  //Hangi sınıftan oluşturacağımzı belirtiyoruz

    fun getData() : Single<List<Besin>>{  //Besin Arayüzü buraya bağladık
        return api.getBesin()
    }




}