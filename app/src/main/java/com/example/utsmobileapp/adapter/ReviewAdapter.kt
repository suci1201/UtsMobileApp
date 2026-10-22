package com.example.utsmobileapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.utsmobileapp.R
import com.example.utsmobileapp.databinding.ItemReviewBinding
import com.example.utsmobileapp.model.ReviewsItem

class ReviewAdapter : ListAdapter<ReviewsItem, ReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: ReviewsItem) {
            val context = itemView.context

            binding.tvReviewer.text = review.reviewerName.orEmpty()
            binding.tvReviewRating.text =
                context.getString(R.string.fmt_review_rating, review.rating ?: 0)
            binding.tvComment.text = review.comment.orEmpty()

            // "2025-04-30T09:41:02.053Z" -> "2025-04-30"
            val tanggal = review.date.orEmpty().substringBefore("T")
            binding.tvReviewMeta.text = "${review.reviewerEmail.orEmpty()}  •  $tanggal"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewsItem>() {

            // Review tidak punya id, jadi pakai kombinasi email + tanggal
            override fun areItemsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
                return oldItem.reviewerEmail == newItem.reviewerEmail &&
                        oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
