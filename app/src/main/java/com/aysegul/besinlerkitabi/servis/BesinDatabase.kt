package com.aysegul.besinlerkitabi.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aysegul.besinlerkitabi.model.Besin

@Database(entities = arrayOf(Besin::class),version = 1 )
abstract class BesinDatabase : RoomDatabase() {

    abstract fun besinDao() : BesinDAO



    //Singleton -> Farklı threadlardan aynı anda sadece tek bir objeye ulaşılabilsin istiyorum

    companion object {
        private var instance : BesinDatabase? = null    //Null bir şekilde besin database i oluşturduk

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock){  //Daha önce invoke çağrıldımı çağrılmadımı onu kontrol ediyorum. Eğer çağrıldıysa hali hazırda instance objesini döndür.Çağrılmadıysa yenisini oluştur
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context : Context) = Room.databaseBuilder(
                context.applicationContext,
                BesinDatabase::class.java,
                "besindatabase"
        ).build()

    }

    //Veritabanını çağırırken invoke fonksiyonunu çağırıcam gerekli işlmleri yapmış olacak


}