package com.example.bestandroidcode.ui.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bestandroidcode.MainActivity
import com.example.bestandroidcode.R
import com.example.bestandroidcode.model.Cat

import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.main_fragment.*


@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    //ViewModel
    //private val viewModel : MainViewModel by viewModels()

    var currentCatObject: Cat? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnLoadCat.setOnClickListener {
            viewModel.getCatRandom()
            observeRandomCatData()
        }

        btnProUser.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_advanceFragment2)
        }
    }

    fun observeRandomCatData() {
        viewModel.randomCatDataList.observe(viewLifecycleOwner, Observer {
            Log.e("Cat",it.body()!!.size.toString())
            currentCatObject = it.body()!!.first()
            Glide.with(this@MainFragment)
                .load(it.body()!!.first().url)
                .into(ivCat)

            val activity = activity as MainActivity
            activity.refreshFavoriteButton(currentCatObject!!.url)
        })
    }

}