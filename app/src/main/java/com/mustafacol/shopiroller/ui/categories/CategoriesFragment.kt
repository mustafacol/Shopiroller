package com.mustafacol.shopiroller.ui.categories

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.shopiroller.R
import com.mustafacol.shopiroller.adapter.CategoryAdapter
import com.mustafacol.shopiroller.adapter.ProductAdapter
import com.mustafacol.shopiroller.databinding.FragmentCategoriesBinding
import com.mustafacol.shopiroller.model.CategoryProductData
import com.mustafacol.shopiroller.model.Data
import com.mustafacol.shopiroller.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private val viewModel by viewModels<CategoriesViewModel>()
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    //category recyclerview
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private var categoryList = mutableListOf<Data>()

    //product recyclerview
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<CategoryProductData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        setUi()
        setupObservers()
        viewModel.getCategories()
        return view
    }

    private fun setUi() {
        categoryAdapter = CategoryAdapter(viewModel, categoryList)
        categoryRecyclerView = binding.recyclerviewCategory
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.adapter = categoryAdapter

        productAdapter = ProductAdapter(productList)
        productRecyclerView = binding.recyclerviewProduct
        productRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        productRecyclerView.adapter = productAdapter

        binding.sortButton.setOnClickListener {
            showSortDialog()
        }

    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.categoryState.collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressbarCategoriesFragment.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressbarCategoriesFragment.visibility = View.GONE
                        categoryList.addAll(it.data!!)
                        categoryAdapter.notifyDataSetChanged()
                    }
                    else -> {
                        binding.progressbarCategoriesFragment.visibility = View.GONE

                        Log.d("ApiError", it.message.toString())
                        Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.selectedCategoryId.collect {
                if (viewModel.selectedCategoryId.value.isNotEmpty())
                    viewModel.getCategoryProducts()
            }
        }
        lifecycleScope.launch {
            viewModel.selectedSort.collect {
                if (viewModel.selectedSort.value.isNotEmpty())
                    viewModel.getCategoryProducts()
            }
        }
        lifecycleScope.launch {
            viewModel.categoryProductsState.collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressbarCategoriesFragment.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressbarCategoriesFragment.visibility = View.GONE
                        productList.clear()
                        productList.addAll(it.data!!)
                        productAdapter.notifyDataSetChanged()
                    }
                    else -> {
                        binding.progressbarCategoriesFragment.visibility = View.GONE

                        Log.d("ApiError", it.message.toString())
                        Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun showSortDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_sort)

        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radioGroup_sort)

        dialog.findViewById<RadioButton>(R.id.radioButton_price).apply {
            setOnClickListener {
                sortDialogClickListener("Price", dialog)
            }
            isChecked = viewModel.selectedSort.value == this.text

        }
        dialog.findViewById<RadioButton>(R.id.radioButton_title).apply {
            isChecked = viewModel.selectedSort.value == this.text
            setOnClickListener {
                sortDialogClickListener("Title", dialog)

            }
        }
        dialog.findViewById<RadioButton>(R.id.radioButton_publishmentDate).apply {
            isChecked = viewModel.selectedSort.value == this.text
            setOnClickListener {
                sortDialogClickListener("PublishmentDate", dialog)
            }
        }
        dialog.show()
    }

    private fun sortDialogClickListener(value: String, dialog: Dialog) {
        viewModel.selectedSort.value = value
        dialog.dismiss()
    }


}