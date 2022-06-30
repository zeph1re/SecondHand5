package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_adapter.view.*
import kotlinx.android.synthetic.main.home_product_adapter.view.product_name
import kotlinx.android.synthetic.main.home_product_adapter.view.product_price
import kotlinx.android.synthetic.main.notification_adapter.view.*

class NotificationAdapter() : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var notification : List<GetNotificationItem>? = null

    fun setNotificationList(notificationList: List<GetNotificationItem>){
        this.notification = notificationList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val notif = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notification, parent, false)

        return NotificationAdapter.ViewHolder(notif)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("notif", notification!![position].imageUrl)
        holder.itemView.notification_product_name.text = "${notification!![position].productId.toString()}"
        holder.itemView.notification_created_at.text = "${notification!![position].transactionDate.toString()}"
        holder.itemView.notifiation_product_price.text = "Rp. ${notification!![position].bidPrice.toString()}"
        holder.itemView.notification_product_offer.text = "${notification!![position].bidPrice.toString()}"
        holder.itemView.notification_info.text = "${notification!![position].bidPrice.toString()}"

        if(notification!![position].imageUrl != null){
            this.let {
                Glide.with(holder.itemView.context).load(notification!![position].imageUrl).into(holder.itemView.notification_image)
            }
        }


    }

    override fun getItemCount(): Int {
        if(notification == null){
            return 0
        }else{
            return notification!!.size
        }
    }


}