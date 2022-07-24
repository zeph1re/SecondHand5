package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerSoldAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_seller_product_sold.*


class SellerProductSold : Fragment() {
//
    private lateinit var sellerOrderSuccessfulAdapter : SellerSoldAdapter
    lateinit var userManager: UserManager

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

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_seller_success_order.adapter = sellerOrderSuccessfulAdapter
        rv_seller_success_order.layoutManager = layoutManager

        val viewModelLogin = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            getSuccessOrder(it)
        }
    }

    private fun getSuccessOrder(token:String){
        val viewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        viewModel.getSellerSuccesfulOrder(token)
        viewModel.sellerSoldOrderLiveData.observe(viewLifecycleOwner){
            if(it != null){
                sellerOrderSuccessfulAdapter.setListProduct(it)
                sellerOrderSuccessfulAdapter.notifyDataSetChanged()

                if(sellerOrderSuccessfulAdapter.itemCount == 0){
                    notfound_sold.visibility = View.VISIBLE

                }else{
                    notfound_sold.visibility = View.GONE

                }


            }
        }
    }


}