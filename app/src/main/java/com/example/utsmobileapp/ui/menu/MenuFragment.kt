package com.example.utsmobileapp.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsmobileapp.R
import com.example.utsmobileapp.adapter.ProductAdapter
import com.example.utsmobileapp.databinding.FragmentMenuBinding
import com.example.utsmobileapp.helpers.Config
import com.example.utsmobileapp.model.ProductItem
import com.example.utsmobileapp.model.ProductResponse
import com.example.utsmobileapp.retrofit.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val adapter = ProductAdapter()
    private var currentCall: Call<ProductResponse>? = null
    private var cachedList: List<ProductItem>? = null

    companion object {
        private const val TAG = "MenuFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.layoutManager = layoutManager
        binding.rvProduct.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvProduct.adapter = adapter


        adapter.setOnItemClickCallback(object : ProductAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProductItem) {
                val json = Gson().toJson(data)
                findNavController().navigate(
                    R.id.action_menu_to_detail,
                    bundleOf(Config.EXTRA_PRODUCT to json)
                )
            }
        })

        binding.swipeRefresh.setOnRefreshListener { getProductList() }

        val cached = cachedList
        if (cached != null) {
            adapter.submitList(cached)
        } else {
            getProductList()
        }
    }

    private fun getProductList() {
        showLoading(true)
        binding.tvError.visibility = View.GONE

        val call = ApiConfig.getApiService().getProducts(Config.PRODUCT_LIMIT, Config.PRODUCT_SELECT)
        currentCall = call

        call.enqueue(object : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (_binding == null) return
                showLoading(false)

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    val list = body.products.orEmpty().filterNotNull()
                    cachedList = list
                    adapter.submitList(list)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                    showError()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                if (call.isCanceled || _binding == null) return
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
                showError()
            }
        })
    }

    private fun showError() {
        // Kalau sudah ada data lama, biarkan tampil; kalau kosong, tampilkan pesan
        if (adapter.itemCount == 0) {
            binding.tvError.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        currentCall?.cancel()
        _binding = null
        super.onDestroyView()
    }
}
