package com.aysegul.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main  // Arka planda ne iş yapılıyorsa yapılacak sonra main e dönülecek


    override fun onCleared() {
        super.onCleared()
        job.cancel()   //Viewmodel da işlemler bitince job ın işlerini de iptal et bitir
    }
}