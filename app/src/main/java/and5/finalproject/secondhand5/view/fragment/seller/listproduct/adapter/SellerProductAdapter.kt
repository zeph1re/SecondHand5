package and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.seller.GetSellerProductItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_adapter.view.*
import kotlinx.android.synthetic.main.home_product_adapter.view.product_image
import kotlinx.android.synthetic.main.home_product_adapter.view.product_name
import kotlinx.android.synthetic.main.seller_product_adapter.view.*

class SellerProductAdapter(var onclick : (GetSellerProductItem)-> Unit) : RecyclerView.Adapter<SellerProductAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
     var categorySellerUpdate = mutableListOf<String>()
    var sellerProduct : List<GetSellerProductItem>? = null
    fun setListProduct(product  : List<GetSellerProductItem>){
        this.sellerProduct= product
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.seller_product_adapter, parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(sellerProduct!![position].imageUrl).into(holder.itemView.product_image)
        holder.itemView.product_name.text = sellerProduct!![position].name
        holder.itemView.seller_product_price.text = "Rp ${sellerProduct!![position].basePrice.toString()}"

        for(j in sellerProduct!![position].categories.indices) {
            categorySellerUpdate.clear()
            sellerProduct!![position].categories.forEach {
               categorySellerUpdate.add(it.name)
            }
            val listToString = categorySellerUpdate.toString()
            val getCategory = listToString.replace("[","").replace("]", "")
            holder.itemView.seller_product_category.text = getCategory
        }



        holder.itemView.productSellerCard.setOnClickListener {
            onclick(sellerProduct!![position])

        }
    }

    override fun getItemCount(): Int {
        if (sellerProduct== null){
            return 0
        }else{
            return sellerProduct!!.size
        }
    }
}