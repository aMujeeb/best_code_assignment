package com.example.bestandroidcode.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bestandroidcode.databinding.FavoriteRowBinding

class FavoriteAdapter(private val myDataset: Array<String>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    class MyViewHolder(val view: FavoriteRowBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = FavoriteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(myDataset[position])
            .into(holder.view.ivCat)
    }

    override fun getItemCount() = myDataset.size
}