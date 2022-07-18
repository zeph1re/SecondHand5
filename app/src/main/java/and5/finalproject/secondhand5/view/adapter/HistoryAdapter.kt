package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.history.GetHistoryItem
import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import and5.finalproject.secondhand5.model.wishlist.GetWishlistProductItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.history_adapter.view.*
import kotlinx.android.synthetic.main.home_product_adapter.view.*
import kotlinx.android.synthetic.main.notification_adapter.view.*
import kotlinx.android.synthetic.main.wishlist_adapter.view.*

class HistoryAdapter (private var onClick : (GetHistoryItem)->Unit) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var historyData : List<GetHistoryItem>? = null
    fun setHistoryList (historyList: List<GetHistoryItem>){
        this.historyData = historyList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val historyView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_adapter, parent, false)

        return ViewHolder(historyView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.history_product_name.text = historyData!![position].productName
        holder.itemView.history_price.text = "Rp. ${historyData!![position].price}"
        holder.itemView.history_status.text = historyData!![position].status
        holder.itemView.history_transaction_date.text = historyData!![position].transactionDate

        if(historyData!![position].imageUrl != null){
            this.let {
                Glide.with(holder.itemView.context).load(historyData!![position].imageUrl).into(holder.itemView.history_image)
            }
        }

        holder.itemView.history_card.setOnClickListener {
            onClick(historyData!![position])
        }


    }

    override fun getItemCount(): Int {
        if(historyData == null){
            return 0
        }else{
            return historyData!!.size
        }
    }


}