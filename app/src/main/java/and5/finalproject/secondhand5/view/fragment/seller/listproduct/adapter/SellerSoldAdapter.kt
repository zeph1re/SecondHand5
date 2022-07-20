package and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.seller.GetSellerOrderItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.seller_interest_adapter.view.*
import java.text.SimpleDateFormat
import java.util.*

class SellerSoldAdapter (var onclick : (GetSellerOrderItem)-> Unit) : RecyclerView.Adapter<SellerSoldAdapter.ViewHolder>(){

    var soldOrder : List<GetSellerOrderItem>? = null

    fun setListProduct(soldorder  : List<GetSellerOrderItem>){
        this.soldOrder = soldorder
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.seller_interest_adapter, parent,false)
        return SellerSoldAdapter.ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(soldOrder!![position].product.imageUrl).into(holder.itemView.gambar)
        holder.itemView.product_name.text = "${soldOrder!![position].product.name}"
        holder.itemView.base_price.text = "Rp ${soldOrder!![position].product.basePrice.toString()}"
        holder.itemView.bid_price.text = "Ditawar Rp ${soldOrder!![position].price.toString()}"
        holder.itemView.status.text = "Status ${soldOrder!![position].product.status.toString()}"


        if(soldOrder!![position].transactionDate != null ){
            val formatter =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val date = formatter.parse(soldOrder!![position].transactionDate.toString())

            holder.itemView.bid_date.text = date.toString()
        }else{
            holder.itemView.bid_date.text = "tanggal null"

        }

        holder.itemView.product_order_card.setOnClickListener {
            onclick(soldOrder!![position])
        }
    }

    override fun getItemCount(): Int {
        if (soldOrder == null){
            return 0
        }else{
            return soldOrder!!.size
        }    }
}