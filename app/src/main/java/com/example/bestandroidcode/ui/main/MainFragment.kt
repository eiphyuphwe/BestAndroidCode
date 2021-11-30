package com.example.bestandroidcode.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.bestandroidcode.MainActivity
import com.example.bestandroidcode.R
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatAPI
import com.example.bestandroidcode.network.ServiceBuilder
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    var currentCatObject: Cat? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btnLoadCat.setOnClickListener {
            val request = ServiceBuilder.buildService(CatAPI::class.java)
            val call = request.getCatRandom()

            call.enqueue(object : Callback<List<Cat>> {
                override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                    if (response.isSuccessful) {

                        currentCatObject = response.body()!!.first()

                        Glide.with(this@MainFragment)
                            .load(response.body()!!.first().url)
                            .into(ivCat)

                        val activity = activity as MainActivity
                        activity.refreshFavoriteButton(currentCatObject!!.url)
                    }
                }

                override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                    Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnProUser.setOnClickListener {
            val advanceFragment = AdvanceFragment()

            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, advanceFragment)
            transaction.addToBackStack("")
            transaction.commit()
        }
    }

}