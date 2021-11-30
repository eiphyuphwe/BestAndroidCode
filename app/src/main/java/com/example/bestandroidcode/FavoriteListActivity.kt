package com.example.bestandroidcode

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_favorite_list.*

class FavoriteListActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)

        title = "Your Favorite Cats"

        val sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE)
        val currentFavoriteList = sharedPref.getStringSet("FAVORITE_LIST", HashSet())

        viewManager = LinearLayoutManager(this)
        viewAdapter = FavoriteAdapter(currentFavoriteList!!.toTypedArray())

        rvFavorite.layoutManager = viewManager
        rvFavorite.adapter = viewAdapter
    }
}