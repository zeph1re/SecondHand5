package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.itemView.product_name.text = "${notification!![position].productId.toString()}"
        holder.itemView.product_price.text = "Rp ${notification!![position]..toString()}"
        holder.itemView.product_id.text = "${productData!![position].id.toString()}"
        holder.itemView.product_category.text = "${productData!![position].categories[0].name.toString()}"


    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}