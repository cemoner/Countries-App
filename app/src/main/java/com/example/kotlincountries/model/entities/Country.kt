package com.example.kotlincountries.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity
data class Country(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val countryName: String?,
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val countryCapital: String?,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    val countryRegion: String?,
    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val countryCurrency: String?,
    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val imageUrl:String?,
    @ColumnInfo(name = "language")
    @SerializedName("language")
    val countryLanguage: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
