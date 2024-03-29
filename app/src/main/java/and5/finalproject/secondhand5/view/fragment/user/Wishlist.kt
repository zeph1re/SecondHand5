@file:Suppress("UnusedImport")

package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.adapter.WishlistAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.WishlistViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_wishlist.*


class Wishlist : Fragment() {

    private lateinit var wishlistAdapter : WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }

        val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""){
                Handler(Looper.getMainLooper()).postDelayed({
                    initWishlist()
                },500)

            }else{
                view.findNavController().navigate(R.id.action_wishlist_to_userNotLogin)
            }
        }
    }



    private fun initWishlist() {

        wishlistAdapter = WishlistAdapter {
            val data = Bundle()
            data.putInt("product_id", it.productId)

            view!!.findNavController().navigate(R.id.action_wishlist_to_productDetail, data)

        }
        val viewModelUser = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        val viewModelWishlist = ViewModelProvider(requireActivity())[WishlistViewModel::class.java]
        viewModelUser.userToken(requireActivity()).observe(viewLifecycleOwner){ token ->
            viewModelWishlist.wishlistProduct.observe(viewLifecycleOwner) {
                if (it != null) {
                    wishlist_rv.layoutManager =
                        GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
                    wishlist_rv.adapter = wishlistAdapter
                    wishlistAdapter.setWishlistProductList(it)
                    wishlistAdapter.notifyDataSetChanged()

                    if(wishlistAdapter.itemCount == 0){
                        notfound_wishlist.visibility = View.VISIBLE
                    }else{
                        notfound_wishlist.visibility = View.GONE
                    }
                }
            }
            viewModelWishlist.getAllWishlistProduct(token)
        }



    }


}