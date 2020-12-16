package com.aysegul.besinlerkitabi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity       // Öncelkle bir sınıfı entity olarak işretleriz.Artık bu su-ınıf SqLite'a tablo olmak için hazır hale gelir.Her entity de mutlaka primary key bulunmalı
data class Besin(
        @ColumnInfo(name = "isim")
        @SerializedName("isim")
        val besinIsim: String?,
        @ColumnInfo(name = "kalori")
        @SerializedName("kalori")
        val besinKalori: String?,
        @ColumnInfo(name = "karbonhidrat")
        @SerializedName("karbonhidrat")
        val besinKarbonhidrat: String?,
        @ColumnInfo(name = "protein")
        @SerializedName("protein")
        val besinProtein: String?,
        @ColumnInfo(name = "yag")
        @SerializedName("yag")
        val besinYag: String?,
        @ColumnInfo(name = "gorsel")
        @SerializedName("gorsel")
        val besinGorsel: String?
      ) {

        @PrimaryKey(autoGenerate = true)
        var uuid :  Int = 0
}