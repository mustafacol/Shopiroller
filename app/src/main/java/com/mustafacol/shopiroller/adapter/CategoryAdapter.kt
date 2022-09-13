package com.mustafacol.shopiroller.adapter

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mustafacol.shopiroller.R
import com.mustafacol.shopiroller.model.Data
import com.mustafacol.shopiroller.ui.categories.CategoriesViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class CategoryAdapter(
    private val categoriesViewModel: CategoriesViewModel,
    private val categoryList: List<Data>

) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var rowIndex = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_cell, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryLayout.setOnClickListener {
            rowIndex = holder.adapterPosition
            categoriesViewModel.viewModelScope.launch {
                categoriesViewModel.selectedCategoryId.emit(categoryList[rowIndex].categoryId)
            }
            notifyDataSetChanged()
        }
        holder.bindView(category, rowIndex)

    }

    override fun getItemCount(): Int = categoryList.size


    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryIcon: ImageView = itemView.findViewById(R.id.icon_category_cell)
        private val categoryTitle: TextView = itemView.findViewById(R.id.title_category_cell)
        val categoryLayout: LinearLayout = itemView.findViewById(R.id.layout_category_cell)
        fun bindView(category: Data, rowIndex: Int) {
            categoryIcon.load(
                category.icon
            )

            categoryTitle.text = category.name

            categoryTitle.setTextColor(
                if (adapterPosition == rowIndex) Color.BLACK
                else Color.WHITE
            )

            categoryIcon.setColorFilter(
                if (adapterPosition == rowIndex) Color.BLACK
                else Color.WHITE
            )


        }
    }
}

