package com.example.bestandroidcode.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestandroidcode.FavoriteAdapter
import com.example.bestandroidcode.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_list_fragment.*

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance() = FavoriteListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.favorite_list_fragment, container, false)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title  = "Your Favorite Cats"
        val sharedPref = activity?.getSharedPreferences("default", Context.MODE_PRIVATE)
        val currentFavoriteList = sharedPref?.getStringSet("FAVORITE_LIST", HashSet())

        viewManager = LinearLayoutManager(activity)
        viewAdapter = FavoriteAdapter(currentFavoriteList!!.toTypedArray())

        rvFavoriteFrag.layoutManager = viewManager
        rvFavoriteFrag.adapter = viewAdapter
    }

}