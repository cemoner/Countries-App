package com.example.kotlincountries.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincountries.R
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeHolderProgressBar
import com.example.kotlincountries.viewmodels.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var viewModel:CountryViewModel
    private var countryID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryID = CountryFragmentArgs.fromBundle(it).countryID
        }


        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryID)

        observeLiveData(view)
    }

    private fun observeLiveData(view: View){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                view.findViewById<TextView>(R.id.countryName).text = it.countryName
                view.findViewById<TextView>(R.id.countryRegion).text = it.countryRegion
                view.findViewById<TextView>(R.id.countryCapital).text = it.countryCapital
                view.findViewById<TextView>(R.id.countryCurrency).text = it.countryCurrency
                view.findViewById<TextView>(R.id.countryLanguage).text = it.countryLanguage
                view.findViewById<ImageView>(R.id.flagImage).downloadFromUrl(it.imageUrl,
                    placeHolderProgressBar(view.context)
                )
            }
        })
    }
}