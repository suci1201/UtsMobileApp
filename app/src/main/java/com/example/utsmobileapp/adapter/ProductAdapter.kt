package com.example.utsmobileapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.utsmobileapp.R
import com.example.utsmobileapp.databinding.ItemProductBinding
import com.example.utsmobileapp.model.ProductItem

class ProductAdapter :
    ListAdapter<ProductItem, ProductAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ProductItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)

        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(product)
        }
    }

    class MyViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductItem) {
            val context = itemView.context

            binding.tvTitle.text = product.title.orEmpty()
            binding.tvCategory.text = product.category.orEmpty()
                .replaceFirstChar { it.uppercase() }
            binding.tvPrice.text = context.getString(R.string.fmt_price, product.price ?: 0.0)
            binding.tvRatingStock.text = context.getString(
                R.string.fmt_rating_stock,
                product.rating ?: 0.0,
                product.stock ?: 0
            )

            Glide.with(context)
                .load(product.thumbnail)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.imgThumbnail)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {

            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
