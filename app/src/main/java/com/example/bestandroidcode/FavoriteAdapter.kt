package com.example.bestandroidcode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.favorite_row.view.*

class FavoriteAdapter(private val myDataset: Array<String>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_row, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(myDataset[position])
            .into(holder.view.ivCat)
    }

    override fun getItemCount() = myDataset.size
}