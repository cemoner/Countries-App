package com.example.kotlincountries.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries.R
import com.example.kotlincountries.view.adapters.CountryAdapter
import com.example.kotlincountries.viewmodels.FeedViewModel

class FeedFragment : Fragment() {

    private lateinit var viewModel:FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        val countryList = view.findViewById<RecyclerView>(R.id.countryList)
        val countryError = view.findViewById<TextView>(R.id.countryError)
        val countryLoading = view.findViewById<ProgressBar>(R.id.countryLoading)

        Log.d("FeedFragment", "countryList is null: ${countryList == null}")
        Log.d("FeedFragment", "countryError is null: ${countryError == null}")
        Log.d("FeedFragment", "countryLoading is null: ${countryLoading == null}")

        countryList.layoutManager = LinearLayoutManager(context)
        countryList.adapter = countryAdapter

        observeLiveData(countryList,countryError,countryLoading)
    }


    fun observeLiveData(countryList:RecyclerView,countryError:TextView,countryLoading:ProgressBar) {
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                 countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if(error){
                    countryError.visibility = View.VISIBLE
                }
                else {
                    countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if(loading){
                    countryLoading.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE
                }
                else {
                    countryLoading.visibility = View.GONE
                }
            }
        })

    }
}