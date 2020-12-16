package com.aysegul.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aysegul.besinlerkitabi.model.Besin
import com.aysegul.besinlerkitabi.servis.BesinAPIServis
import com.aysegul.besinlerkitabi.servis.BesinDatabase
import com.aysegul.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10*60*1000*1000*1000L

    private val besinApiServis = BesinAPIServis()
    private val disposable = CompositeDisposable()  //Çok fazla istekte bulunduğumuzda,Ne zaman işimz biterse o zaman çağırıp kurtulabileceğimiz bir yapı(Kullan at)
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if(kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            //Sqlite'dan çek
            verileriSQLiteteanAl()
        }
        else{
            verileriInternettenAl()
        }
    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    fun verileriSQLiteteanAl(){
        besinYukleniyor.value = true

        launch {
            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri room dan aldık",Toast.LENGTH_LONG).show()
        }

    }

    fun verileriInternettenAl(){
        besinYukleniyor.value = true  //progressbar çalışssın

        //IO , Default ,
        disposable.add(
                besinApiServis.getData()
                        .subscribeOn(Schedulers.newThread())  //Single gözlemlenebilir objessine kayıt oluyoruz Ve Nerde kayıt olacağımız
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Besin>>() {
                            override fun onSuccess(t: List<Besin>) {
                                //Başarılı Olursak
                                sqliteSakla(t)
                                Toast.makeText(getApplication(),"Besinleri internetten aldık",Toast.LENGTH_LONG).show()

                            }

                            override fun onError(e: Throwable) {
                               //Hata Alırsak
                                besinHataMesaji.value = true
                                besinYukleniyor.value = false
                                e.printStackTrace()
                            }

                        })

        )


    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinListesi : List<Besin>){
        //Yeni bir coroutine oluşturulur.Güncel thread neyse onu bloklamaz farklı bir threadde işlemleri yapar ve burdaki coroutine bir iş olarak çalıştırılır
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())   //Tüm ekleme işlemlerini yapıp geriye uuid listesi döndürüyo
            var i = 0
            while (i < besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i = i+1
            }
            besinleriGoster(besinListesi)

        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }

}