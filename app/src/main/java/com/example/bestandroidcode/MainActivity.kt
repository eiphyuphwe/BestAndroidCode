package com.example.bestandroidcode

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bestandroidcode.ui.main.AdvanceFragment
import com.example.bestandroidcode.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Best Android Code does not need comments to explain the code

    private lateinit var sharedPref: SharedPreferences

    private var mOptionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        mOptionsMenu = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {

            val currentFavoriteList = sharedPref.getStringSet("FAVORITE_LIST", HashSet())
            val f: Fragment? = my_nav_host_fragment.childFragmentManager.fragments.get(0)

            if (f is MainFragment) {
                if (f.currentCatObject != null) {
                    val catImageUrl = f.currentCatObject!!.url

                    if (catImageUrl.isNotBlank()) {
                        if (currentFavoriteList!!.contains(catImageUrl)) {
                            currentFavoriteList!!.remove(catImageUrl)
                        } else {
                            currentFavoriteList!!.add(catImageUrl)
                        }

                        val e: SharedPreferences.Editor = sharedPref.edit()
                        e.remove("FAVORITE_LIST")
                        e.commit()

                        e.putStringSet("FAVORITE_LIST", currentFavoriteList)
                        e.commit()

                        refreshFavoriteButton(catImageUrl)
                    }
                }
            }

            if (f is AdvanceFragment) {
                if (f.currentCatObject != null) {
                    val catImageUrl = f.currentCatObject!!.url

                    if (catImageUrl.isNotBlank()) {
                        if (currentFavoriteList!!.contains(catImageUrl)) {
                            currentFavoriteList!!.remove(catImageUrl)
                        } else {
                            currentFavoriteList!!.add(catImageUrl)
                        }

                        val e: SharedPreferences.Editor = sharedPref.edit()
                        e.remove("FAVORITE_LIST")
                        e.commit()

                        e.putStringSet("FAVORITE_LIST", currentFavoriteList)
                        e.commit()

                        refreshFavoriteButton(catImageUrl)
                    }
                }
            }

            true
        }

        R.id.action_favorite_list -> {
            val intent = Intent(this, FavoriteListActivity::class.java)
            startActivity(intent)

            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun refreshFavoriteButton(catImageUrl: String) {
        if (mOptionsMenu != null) {
            val currentFavoriteList = sharedPref.getStringSet("FAVORITE_LIST", HashSet())

            val favoriteItem = mOptionsMenu!!.findItem(R.id.action_favorite)

            if (currentFavoriteList!!.contains(catImageUrl)) {
                favoriteItem.setIcon(R.drawable.baseline_favorite_black_24)
            } else {
                favoriteItem.setIcon(R.drawable.baseline_favorite_border_black_24)
            }
        }
    }
}