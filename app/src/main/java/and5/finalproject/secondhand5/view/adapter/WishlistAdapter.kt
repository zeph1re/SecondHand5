package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.wishlist.GetWishlistProductItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.wishlist_adapter.view.*

class WishlistAdapter(private var onClick : (GetWishlistProductItem)->Unit) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    private var wishlistProduct : List<GetWishlistProductItem>? = null
    fun setWishlistProductList(wishlistProductList: List<GetWishlistProductItem>){
        this.wishlistProduct = wishlistProductList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val wishlist = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_adapter, parent, false)

        return ViewHolder(wishlist)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if  (wishlistProduct!![position].product != null) {
            holder.itemView.wishlist_name.text = wishlistProduct!![position].product.name
            holder.itemView.wishlist_location.text = wishlistProduct!![position].product.location
            holder.itemView.wishlist_price.text = "Rp${wishlistProduct!![position].product.basePrice}"
        } else {
            holder.itemView.wishlist_name.text = "null"
            holder.itemView.wishlist_location.text = "null"
            holder.itemView.wishlist_price.text = "null"
        }


        if(wishlistProduct!![position].product.imageUrl != null){
            this.let {
                Glide.with(holder.itemView.context).load(wishlistProduct!![position].product.imageUrl).into(holder.itemView.wishlist_image)
            }
        } else {
            Glide.with(holder.itemView.context).load(R.drawable.ic_launcher_background).into(holder.itemView.wishlist_image)
        }

        holder.itemView.wishlist_product_card.setOnClickListener {
            onClick(wishlistProduct!![position])
        }


    }

    override fun getItemCount(): Int {
        return if(wishlistProduct == null){
            0
        }else{
            wishlistProduct!!.size
        }
    }


}