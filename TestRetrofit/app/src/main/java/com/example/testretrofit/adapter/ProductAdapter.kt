package com.example.testretrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testretrofit.R
import com.example.testretrofit.databinding.ListItemBinding
import com.example.testretrofit.models.Product

class ProductAdapter: ListAdapter<Product,ProductAdapter.Holder>/*вним.импорт*/(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding=ListItemBinding.bind(view)

        fun bind(product: Product){
            binding.title222.text=product.title
            binding.description.text=product.description
        }
    }
    class Comparator:DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem==newItem
        }

    }

}