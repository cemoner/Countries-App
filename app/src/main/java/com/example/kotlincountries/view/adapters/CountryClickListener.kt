package com.example.kotlincountries.view.adapters

import android.view.View
import com.example.kotlincountries.model.entities.Country

interface CountryClickListener {

    fun onCountryClicked(view: View,country:Country)

    fun onDeleteClicked(view: View,country:Country)
}