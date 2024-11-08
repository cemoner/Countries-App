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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.Visibility
import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.FragmentFeedBinding
import com.example.kotlincountries.view.adapters.CountryAdapter
import com.example.kotlincountries.viewmodels.FeedViewModel

class FeedFragment : Fragment() {

    private lateinit var viewModel:FeedViewModel
    private lateinit var countryAdapter:CountryAdapter
    private lateinit var dataBinding:FragmentFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_feed, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        countryAdapter= CountryAdapter(arrayListOf(),viewModel)
        viewModel.refreshData()

        dataBinding.viewModel = viewModel

        val countryList = view.findViewById<RecyclerView>(R.id.countryList)
        val countryError = view.findViewById<TextView>(R.id.countryError)
        val countryLoading = view.findViewById<ProgressBar>(R.id.countryLoading)


        countryList.layoutManager = LinearLayoutManager(context)
        countryList.adapter = countryAdapter

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.VISIBLE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

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