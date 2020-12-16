package com.aysegul.besinlerkitabi.servis

import com.aysegul.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinAPI {


    //GET-POST

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //BASE-URL -> https://raw.githubusercontent.com/
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<Besin>>  //Bir defa veriyi çekiyor ve duruyor ->Bir hata mesajı ya da veriyi döndürecek
}