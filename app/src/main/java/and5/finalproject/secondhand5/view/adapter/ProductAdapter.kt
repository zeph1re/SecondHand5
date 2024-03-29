package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_adapter.view.*

class ProductAdapter (private var onClick : (GetProductItem)->Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private var productData : List<GetProductItem>? = null
    private var categoryProduct = mutableListOf<String>()
    fun setProductList(productList: List<GetProductItem>){
        this.productData = productList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val uiProduct = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_product_adapter, parent, false)

        return ViewHolder(uiProduct)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        if(productData!![position].name != null){
            holder.itemView.product_name.text = productData!![position].name
        }else{
            holder.itemView.product_name.text = "null"
        }

        if(productData!![position].basePrice != null){
            holder.itemView.product_price.text = "Rp ${productData!![position].basePrice}"
        }else{
            holder.itemView.product_price.text = "null"
        }



        if(productData!![position].id != null){
            holder.itemView.product_id.text = "${productData!![position].id}"
        }else{
            holder.itemView.product_id.text = "null"
        }
             categoryProduct.clear()
            for(j in productData!![position].categories.indices){
                productData!![position].categories.forEach {

                    categoryProduct.add(it.name)
                }
                val listToString = categoryProduct.toString()
                Log.d("category", listToString)
                val getCategory = listToString.replace("[","").replace("]", "")
                holder.itemView.product_category.text = getCategory
            }



        if(productData!![position].imageUrl != null){
            this.let {
                Glide.with(holder.itemView.context).load(productData!![position].imageUrl).into(holder.itemView.product_image)
            }
        }

        holder.itemView.productCard.setOnClickListener {
            onClick(productData!![position])
        }
    }

    override fun getItemCount(): Int {
        return if(productData == null){
            0
        }else{
            productData!!.size
        }
    }


}