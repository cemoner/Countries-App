package com.example.kotlincountries.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
data class Country(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var countryName: String?,
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    var countryCapital: String?,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    var countryRegion: String?,
    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    var countryCurrency: String?,
    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val imageUrl:String?,
    @ColumnInfo(name = "language")
    @SerializedName("language")
    var countryLanguage: String?

):Serializable{
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
