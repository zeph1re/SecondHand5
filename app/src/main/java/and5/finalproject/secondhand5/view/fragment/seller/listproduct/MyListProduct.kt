package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.ViewPagerAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_list_product.*
import kotlinx.android.synthetic.main.fragment_my_list_product.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class MyListProduct : Fragment() {

    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userManager = UserManager(requireActivity())
        return if (userManager.userToken.toString() != "") {
            inflater.inflate(R.layout.fragment_my_list_product, container, false)
        } else {
            inflater.inflate(R.layout.fragment_user_not_login, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        val viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModel.userToken(requireActivity()).observe(viewLifecycleOwner){
            detailUser(it)
        }
        edit_seller.setOnClickListener {
            findNavController().navigate(R.id.profile)
        }
    }

    private fun detailUser(token:String) {
        val viewModelUser = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModelUser.getUserData(token)
        viewModelUser.getUserData.observe(viewLifecycleOwner){
            Log.d("namaaaa", it.fullName)
            Log.d("cityyyy", it.city)
            seller_name.setText(it.fullName)
            seller_address.setText(it.city)
            Glide.with(requireActivity()).load( it.imageUrl).into(view!!.seller_image)
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