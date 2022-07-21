package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.connectivity.CheckConnectivity
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.ViewPagerAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_list_product.*
import kotlinx.android.synthetic.main.fragment_my_list_product.view.*


class MyListProduct : Fragment() {
    var connectivity: CheckConnectivity = CheckConnectivity()
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
        if (connectivity.isOnline(requireContext())) {
            val loginViewModel =
                ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
            loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner) { token ->
                if (token != "" || token == null) {
                    initLayout()
                    val viewModel =
                        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
                    viewModel.userToken(requireActivity()).observe(viewLifecycleOwner) {
                        detailUser(it)
                    }
                    edit_seller.setOnClickListener {
                        findNavController().navigate(R.id.profile)
                    }
                } else {
                    view.findNavController().navigate(R.id.action_myListProduct_to_userNotLogin)
                }
            }
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
            Glide.with(requireActivity()).load( it.imageUrl).into(requireView().seller_image)
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