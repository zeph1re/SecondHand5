package and5.finalproject.secondhand5.Room.Adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.Room.Model.GetProductHome
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_adapter.view.*


class AdapterHome (private var listdata: List<GetProductHome>): RecyclerView.Adapter<AdapterHome.ViewHolder>(){

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_product_adapter, parent, false)

        return  ViewHolder(viewitem)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.product_name.text = listdata[position].name
        holder.itemView.product_price.text = listdata[position].basePrice.toString()
        holder.itemView.product_category.text = listdata[position].categories.toString()
        Glide.with(holder.itemView.context).load( listdata[position].imageUrl).into(holder.itemView.product_image)



    }


    override fun getItemCount(): Int {
        return listdata.size
    }
}