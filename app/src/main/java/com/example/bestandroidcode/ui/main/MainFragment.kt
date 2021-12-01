package com.example.bestandroidcode.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bestandroidcode.MainActivity
import com.example.bestandroidcode.R
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatAPI
import com.example.bestandroidcode.network.ServiceBuilder
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val advanceFragment = AdvanceFragment()

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, advanceFragment)
            transaction.addToBackStack("")
            transaction.commit()
        }
    }

    fun observeRandomCatData() {
        viewModel.randomCatDataList.observe(viewLifecycleOwner, Observer {
            currentCatObject = it.body()!!.first()
            Glide.with(this@MainFragment)
                .load(it.body()!!.first().url)
                .into(ivCat)

            val activity = activity as MainActivity
            activity.refreshFavoriteButton(currentCatObject!!.url)
        })
    }

}