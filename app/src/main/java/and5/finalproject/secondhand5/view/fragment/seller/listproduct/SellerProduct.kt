package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerProductAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_seller_product.*
import kotlinx.android.synthetic.main.home_product_adapter.*
import kotlinx.android.synthetic.main.seller_product_adapter.*
import kotlinx.android.synthetic.main.seller_product_adapter.view.*


class SellerProduct : Fragment() {
    lateinit var myListProductAdapter: SellerProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_seller_product, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myListProductAdapter= SellerProductAdapter{
            //navigasi on click
        }
        var style = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        myListProduct_recyclerview.adapter = myListProductAdapter
        myListProduct_recyclerview.layoutManager = style

        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            getProduct(it)
        }

    }

    fun getProduct(token:String){
        val viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModel.getSellerProduct(token)
        viewModel.sellerProductLiveData.observe(viewLifecycleOwner){
            if(it != null){
                myListProductAdapter.setListProduct(it)
                myListProductAdapter.notifyDataSetChanged()

            }
        }
    }

}