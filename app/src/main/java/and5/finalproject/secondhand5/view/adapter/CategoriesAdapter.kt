package and5.finalproject.secondhand5.view.adapter

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.seller.Category
import and5.finalproject.secondhand5.model.seller.GetSellerCategoryItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_categories_adapter.view.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var category : List<GetSellerCategoryItem>? = null

    fun setProductList(categoryList: List<GetSellerCategoryItem>){
        this.category = categoryList
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ui = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_categories_adapter, parent, false)

        return ViewHolder(ui)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.home_category.text = "${category!![position].name.toString()}"

    }

    override fun getItemCount(): Int {
        if(category == null){
            return 0
        }else{
            return category!!.size
        }
    }
}