package com.example.kotlincountries.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.CountryItemBinding
import com.example.kotlincountries.model.entities.Country
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeHolderProgressBar
import com.example.kotlincountries.view.fragments.FeedFragmentDirections
import com.example.kotlincountries.viewmodels.FeedViewModel
import com.google.android.material.snackbar.Snackbar

class CountryAdapter(val countryList:ArrayList<Country>,val viewModel: FeedViewModel):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {

    inner class CountryViewHolder(var countryBinding: CountryItemBinding):RecyclerView.ViewHolder(countryBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CountryItemBinding>(inflater,R.layout.country_item,parent,false)
        return CountryViewHolder(countryBinding = binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.countryBinding.country = countryList[position]
        holder.countryBinding.listener = this
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(view: View,country: Country) {
        val name = country.countryName
        val action = name?.let { FeedFragmentDirections.actionFeedFragmentToCountryFragment(it) }
        if (action != null) {
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onDeleteClicked(view: View,country: Country) {
        val uuid = country.uuid
        Snackbar.make(view,"Do you want to delete ${country.countryName}?",Snackbar.LENGTH_LONG).setAction("Yes"){
            country.countryName?.let { it1 -> viewModel.deleteCountry(it1) }
            Snackbar.make(it,"Deletion is Succesful!",Snackbar.LENGTH_SHORT).show()
        }.show()
    }
}