package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.notification_adapter.view.*

class NotificationAdapter(private var onClick : (GetNotificationItem)->Unit) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var notification : List<GetNotificationItem>? = null
    var getProductBasePrice = mutableListOf<String>()
    fun setNotificationList(notificationList: List<GetNotificationItem>){
        this.notification = notificationList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val notif = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_adapter, parent, false)

        return ViewHolder(notif)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.cardNotification.setOnClickListener {
            onClick(notification!![position])
        }

        if(notification!![position].status == "bid" && notification!![position].notificationType == "seller" ){

            holder.itemView.cardview_product_offer.visibility = View.VISIBLE

            holder.itemView.notification_product_name.text = "${notification!![position].productName}"
            holder.itemView.notification_created_at.text = "${notification!![position].transactionDate.toString()}"
            holder.itemView.notifiation_product_price.text = "Dari Rp ${notification!![position].basePrice.toString()}"

            holder.itemView.notification_product_offer.text = "Ditawar Rp ${notification!![position].bidPrice.toString()}"

            if(notification!![position].read == true){
                holder.itemView.read_or_not.visibility = View.GONE
            }

            if(notification!![position].imageUrl != null){
                this.let {
                    Glide.with(holder.itemView.context).load(notification!![position].imageUrl).into(holder.itemView.notification_image)
                }
            }

        }

        else if(notification!![position].notificationType == "seller" && notification!![position].status == "create"){

            holder.itemView.cardview_product_add.visibility = View.VISIBLE

            if(notification!![position].imageUrl != null){
                this.let {
                    Glide.with(holder.itemView.context).load(notification!![position].imageUrl).into(holder.itemView.notification_image_product_add)
                }
            }
            holder.itemView.notification_created_at_product_add.text = notification!![position].createdAt
            holder.itemView.notification_product_name_product_add.text = "${notification!![position].productName}"
            holder.itemView.notifiation_product_price_product_add.text = "Seharga Rp ${notification!![position].basePrice.toString()} "

            if(notification!![position].read == true){
                holder.itemView.read_or_not_product_add.visibility = View.GONE
            }
        }

        else if(notification!![position].notificationType == "buyer" && notification!![position].status == "accepted"){
            holder.itemView.cardview_order_response.visibility = View.VISIBLE

            if(notification!![position].read == true){
                holder.itemView.read_or_not_order_response.visibility = View.GONE
            }

            holder.itemView.notification_created_at_order_response.text = "${notification!![position].transactionDate.toString()}"
            holder.itemView.notification_seller_answer_order_response.text = "Penawaranmu untuk ${notification!![position].sellerName} diterima oleh ${notification!![position].productName}"

            holder.itemView.notification_product_name_order_response.text = "${notification!![position].productName.toString()}"

            if(notification!![position].imageUrl != null){
                this.let {
                    Glide.with(holder.itemView.context).load(notification!![position].imageUrl).into(holder.itemView.notification_image_product_order_response)
                }
            }

        }

        else if(notification!![position].notificationType == "buyer" && notification!![position].status == "declined"){
            holder.itemView.cardview_order_response.visibility = View.VISIBLE

            if(notification!![position].read == true){
                holder.itemView.read_or_not_order_response.visibility = View.GONE
            }

            holder.itemView.notification_created_at_order_response.text = "${notification!![position].transactionDate.toString()}"
            holder.itemView.notification_seller_answer_order_response.text = "Penawaranmu untuk ${notification!![position].sellerName} ditolak oleh ${notification!![position].productName}"
            holder.itemView.notification_product_name_order_response.text = "${notification!![position].productName.toString()}"


            if(notification!![position].imageUrl != null){
                this.let {
                    Glide.with(holder.itemView.context).load(notification!![position].imageUrl).into(holder.itemView.notification_image_product_order_response)
                }
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