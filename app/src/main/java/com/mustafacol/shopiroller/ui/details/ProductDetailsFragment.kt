package com.mustafacol.shopiroller.ui.details

import android.graphics.Paint
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.mustafacol.shopiroller.R
import com.mustafacol.shopiroller.databinding.FragmentCategoriesBinding
import com.mustafacol.shopiroller.databinding.FragmentProductDetailsBinding
import com.mustafacol.shopiroller.model.ProductDetailData
import com.mustafacol.shopiroller.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {


    private val viewModel by viewModels<ProductDetailsViewModel>()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val productId = arguments?.getString("productId")
        _binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        setObservers()
        if (productId != null)
            viewModel.getProductDetails(productId)

        return view
    }

    private fun setUi(data: ProductDetailData) {
        binding.imageDetailsFragment.load(
            data.featuredImage.n
        )
        binding.titleDetailsFragment.text = data.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.descriptionDetailsFragment.text = Html.fromHtml(
                data.description, Html.FROM_HTML_MODE_COMPACT
            )
        } else
            binding.descriptionDetailsFragment.text = Html.fromHtml(
                data.description
            )
        binding.descriptionDetailsFragment.movementMethod = ScrollingMovementMethod()
        if (data.campaignPrice != 0.0) {
            binding.discountPriceDetailsFragment.text = "$${String.format("%.2f", data.price)}"
            binding.discountPriceDetailsFragment.visibility = View.VISIBLE
            binding.discountPriceDetailsFragment.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.priceDetailsFragment.text = "$${String.format("%.2f", data.campaignPrice)}"
        } else {
            binding.discountPriceDetailsFragment.visibility = View.INVISIBLE
            binding.priceDetailsFragment.text = "$${String.format("%.2f", data.price)}"
        }

        binding.noStockDetailsFragment.visibility =
            if (data.stock > 0) View.INVISIBLE else View.VISIBLE

        binding.buttonAddCartDetailsFragment.setOnClickListener {
            Toast.makeText(
                requireContext(),
                if (data.stock > 0) "Product is added to card" else "Out of stock",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.productDetailsState.collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressbarDetailsFragment.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressbarDetailsFragment.visibility = View.GONE
                        setUi(it.data!!)
                    }
                    else -> {
                        binding.progressbarDetailsFragment.visibility = View.GONE

                        Log.d("ApiError", it.message.toString())
                        Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


}