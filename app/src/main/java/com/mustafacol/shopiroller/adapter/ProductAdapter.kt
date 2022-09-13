package com.mustafacol.shopiroller.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.mustafacol.shopiroller.R
import com.mustafacol.shopiroller.model.CategoryProductData

class ProductAdapter(
    private val productList:List<CategoryProductData>
):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_cell, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product= productList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productLayout= itemView.findViewById<ConstraintLayout>(R.id.layout_product_cell)
        private val productImage = itemView.findViewById<ImageView>(R.id.icon_product_cell)
        private val productTitle = itemView.findViewById<TextView>(R.id.title_product_cell)
        private val productDiscountPrice = itemView.findViewById<TextView>(R.id.discount_product_cell)
        private val productPrice = itemView.findViewById<TextView>(R.id.price_product_cell)
        fun bindView(product:CategoryProductData){
            productImage.load(
                product.featuredImage.t
            ){
                scale(Scale.FIT)

            }
            productTitle.text = product.title
            if(product.campaignPrice!=null){
                productDiscountPrice.text = "$${String.format("%.2f",product.price)}"
                productDiscountPrice.visibility = View.VISIBLE
                productDiscountPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                productPrice.text = "$${String.format("%.2f",product.campaignPrice)}"
            }else{
                productDiscountPrice.visibility = View.INVISIBLE
                productPrice.text= "$${String.format("%.2f",product.price)}"
            }

            productLayout.setOnClickListener {
                val bundle= bundleOf("productId" to product.id)
                it.findNavController().navigate(R.id.action_categoriesFragment_to_productDetailsFragment,bundle)
            }

        }
    }
}