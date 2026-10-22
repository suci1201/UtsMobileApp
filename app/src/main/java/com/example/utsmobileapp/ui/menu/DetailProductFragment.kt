package com.example.utsmobileapp.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsmobileapp.R
import com.example.utsmobileapp.adapter.ImageAdapter
import com.example.utsmobileapp.adapter.ReviewAdapter
import com.example.utsmobileapp.databinding.FragmentDetailProductBinding
import com.example.utsmobileapp.helpers.Config
import com.example.utsmobileapp.model.ProductItem
import com.google.gson.Gson

class DetailProductFragment : Fragment() {

    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = arguments?.getString(Config.EXTRA_PRODUCT)
        if (json.isNullOrEmpty()) {
            findNavController().popBackStack()
            return
        }

        val data = Gson().fromJson(json, ProductItem::class.java)
        val images = data.images.orEmpty().filterNotNull()
            .ifEmpty { listOfNotNull(data.thumbnail) }
        val imageAdapter = ImageAdapter()
        binding.rvImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvImages.adapter = imageAdapter
        imageAdapter.submitList(images)
        binding.tvTitle.text = data.title.orEmpty()
        binding.tvCategory.text = data.category.orEmpty().replaceFirstChar { it.uppercase() }
        binding.tvPrice.text = getString(R.string.fmt_price, data.price ?: 0.0)
        binding.tvId.text = getString(R.string.fmt_id, data.id ?: 0)
        binding.tvRating.text = getString(R.string.fmt_rating, data.rating ?: 0.0)
        binding.tvStock.text = getString(R.string.fmt_stock, data.stock ?: 0)
        binding.tvAvailability.text =
            getString(R.string.fmt_availability, data.availabilityStatus.orEmpty())

        val tags = data.tags.orEmpty().filterNotNull()
        binding.tvTags.text = getString(
            R.string.fmt_tags,
            if (tags.isEmpty()) "-" else tags.joinToString(", ")
        )

        val dim = data.dimensions
        binding.tvDimensions.text = getString(
            R.string.fmt_dimensions,
            dim?.width ?: 0.0,
            dim?.height ?: 0.0,
            dim?.depth ?: 0.0
        )
        binding.tvDescription.text = data.description.orEmpty()

        val reviews = data.reviews.orEmpty().filterNotNull()
        val reviewAdapter = ReviewAdapter()
        binding.tvReviewsTitle.text = getString(R.string.fmt_reviews_title, reviews.size)
        binding.rvReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReviews.adapter = reviewAdapter
        reviewAdapter.submitList(reviews)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
