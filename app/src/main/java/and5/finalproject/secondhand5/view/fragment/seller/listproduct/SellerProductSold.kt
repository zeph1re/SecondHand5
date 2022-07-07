package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerOrderAdapter
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerSoldAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_seller_product_sold.*
import kotlin.properties.Delegates


class SellerProductSold : Fragment() {
//
    lateinit var sellerOrderSuccessfulAdapter : SellerSoldAdapter
    lateinit var userManager: UserManager
    var orderId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seller_product_sold, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sellerOrderSuccessfulAdapter = SellerSoldAdapter{
            //tes
//            sellerResponse(it.id)
//            val data = Bundle()
//            data.putInt("order_id", it.id)
//            view.findNavController().navigate(R.id.action_myListProduct_to_detailPenawar, data)
        }

        var layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_seller_success_order.adapter = sellerOrderSuccessfulAdapter
        rv_seller_success_order.layoutManager = layoutManager

        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            getSuccessOrder(it)
        }
    }

    fun getSuccessOrder(token:String){
        val viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModel.getSellerSuccesfulOrder(token)
        viewModel.sellerSoldOrderLiveData.observe(viewLifecycleOwner){
            if(it != null){
                sellerOrderSuccessfulAdapter.setListProduct(it)
                sellerOrderSuccessfulAdapter.notifyDataSetChanged()
            }
        }
    }


}