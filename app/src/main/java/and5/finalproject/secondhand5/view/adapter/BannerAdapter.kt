package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.banner.GetBannerItem
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_banner_adapter.view.*

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private var banner : List<GetBannerItem>? = null

    fun setBannerList(bannerList: List<GetBannerItem>){
        this.banner = bannerList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ui = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_banner_adapter, parent, false)
        return ViewHolder(ui)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("banner", banner!![position].imageUrl)
        Glide.with(holder.itemView.context).load(banner!![position].imageUrl).into(holder.itemView.image_banner)

    }

    override fun getItemCount(): Int {
        if(banner == null){
            return 0
        }else{
            return banner!!.size
        }
    }
}