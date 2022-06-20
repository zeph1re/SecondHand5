package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.getProductItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_adapter.view.*

class ProductAdapter () : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private var productData : List<getProductItem>? = null

    fun setProductList(productList: List<getProductItem>){
        this.productData = productList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val uiProduct = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_product_adapter, parent, false)

        return ViewHolder(uiProduct)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.product_name.text = "${productData!![position].name.toString()}"
        holder.itemView.product_price.text = "Rp ${productData!![position].basePrice.toString()}"

        this.let {
            Glide.with(holder.itemView.context).load(productData!![position].imageUrl).into(holder.itemView.product_image)
        }
    }

    override fun getItemCount(): Int {
        if(productData == null){
            return 0
        }else{
            return productData!!.size
        }
    }


}