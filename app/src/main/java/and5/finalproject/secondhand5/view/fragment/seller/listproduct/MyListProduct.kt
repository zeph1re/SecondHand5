package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.ViewPagerAdapter
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_list_product.*


class MyListProduct : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_list_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        detailUser()
    }

    private fun detailUser() {
        val viewModelUser = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModelUser.getUserData.observe(viewLifecycleOwner){
            Log.d("namaaaa", it.fullName)
            Log.d("cityyyy", it.city)
            seller_name.setText(it.fullName)
            seller_address.setText(it.city)
        }
    }

    private fun initLayout() {
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewpager_sellerproduct.adapter = adapter

        TabLayoutMediator(seller_product_tablayout,viewpager_sellerproduct){tab,position ->
            when(position){
                0-> {
                    tab.text = "Product"
                }
                1-> {
                    tab.text = "Interest"
                }
                2-> {
                    tab.text = "Sold"
                }
            }
        }.attach()


    }
}