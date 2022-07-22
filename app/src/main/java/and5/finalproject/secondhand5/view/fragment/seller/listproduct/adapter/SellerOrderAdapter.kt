package and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.seller.GetSellerOrderItem
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.seller_interest_adapter.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.days

// atau Seller Product Interest adapter
class SellerOrderAdapter(var onclick : (GetSellerOrderItem)-> Unit) : RecyclerView.Adapter<SellerOrderAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    var sellerOrder : List<GetSellerOrderItem>? = null

    fun setListProduct(ordered  : List<GetSellerOrderItem>){
        this.sellerOrder = ordered
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.seller_interest_adapter, parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(sellerOrder!![position].product.status == "sold"){
            holder.itemView.product_order_card.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }else{

        Glide.with(holder.itemView.context).load(sellerOrder!![position].product.imageUrl).into(holder.itemView.gambar)
        holder.itemView.product_name.text = "${sellerOrder!![position].product.name}"
        holder.itemView.base_price.text = "Rp ${sellerOrder!![position].product.basePrice.toString()}"
        holder.itemView.bid_price.text = "Ditawar Rp ${sellerOrder!![position].price.toString()}"
        holder.itemView.status.text = "Status ${sellerOrder!![position].product.status.toString()}"


        if(sellerOrder!![position].createdAt != null ){
            val formatter =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val date = formatter.parse(sellerOrder!![position].createdAt.toString())

            holder.itemView.bid_date.text = date.toString()
            Log.d("testes tgl",  date.toString())
            Log.d("testes tgl",  sellerOrder!![position].createdAt.toString())

        }else{
            holder.itemView.bid_date.text = "tanggal null"

        }

        holder.itemView.product_order_card.setOnClickListener {
            onclick(sellerOrder!![position])
        }


    }

    override fun getItemCount(): Int {
        if (sellerOrder == null){
            return 0
        }else{
            return sellerOrder!!.size
        }
    }
}