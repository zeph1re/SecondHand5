package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerOrderAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_seller_interest.*


class SellerProductInterest : Fragment() {

    private lateinit var sellerOrderAdapter: SellerOrderAdapter
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seller_interest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireActivity())

        sellerOrderAdapter = SellerOrderAdapter{
            //tes
//            sellerResponse(it.id)
            val data = Bundle()
            data.putInt("order_id", it.id)
            view.findNavController().navigate(R.id.action_myListProduct_to_detailPenawar, data)
        }

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_seller_order.adapter = sellerOrderAdapter
        rv_seller_order.layoutManager = layoutManager

        val viewModelLogin = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            getOrder(it)
        }
    }

    private fun getOrder(token:String){
        val viewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        viewModel.getSellerOrder(token)
        viewModel.sellerOrderLiveData.observe(viewLifecycleOwner){
            if(it != null){
                sellerOrderAdapter.setListProduct(it)
                sellerOrderAdapter.notifyDataSetChanged()
            }
        }
    }


}