package and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter

import and5.finalproject.secondhand5.view.fragment.seller.listproduct.SellerProduct
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.SellerProductInterest
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.SellerProductSold
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SellerProduct()
            }
            1 -> {
                SellerProductInterest()
            }
            2 -> {
                SellerProductSold()
            }
            else -> {
                Fragment()
            }
        }
    }
}