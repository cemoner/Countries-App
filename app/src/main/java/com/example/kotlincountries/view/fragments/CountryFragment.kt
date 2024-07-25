package com.example.kotlincountries.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.FragmentCountryBinding
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeHolderProgressBar
import com.example.kotlincountries.viewmodels.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var viewModel:CountryViewModel
    private var countryID = 0
    private lateinit var dataBinding : FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root
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
                dataBinding.selectedCountry = it
            }
        })
    }
}