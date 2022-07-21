package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerOrderAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_seller_interest.*
import kotlin.properties.Delegates


class SellerProductInterest : Fragment() {

    lateinit var sellerOrderAdapter: SellerOrderAdapter
    lateinit var userManager: UserManager
    var orderId by Delegates.notNull<Int>()

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


        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            getOrder(it)
        }

        sellerOrderAdapter = SellerOrderAdapter{
            //tes
//            sellerResponse(it.id)
            val data = Bundle()
            data.putInt("order_id", it.id)
            view.findNavController().navigate(R.id.action_myListProduct_to_detailPenawar, data)
        }
        var layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_seller_order.adapter = sellerOrderAdapter
        rv_seller_order.layoutManager = layoutManager
    }

    fun getOrder(token:String){
        val viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModel.getSellerOrder(token)
        viewModel.sellerOrderLiveData.observe(viewLifecycleOwner){
            if(it != null){
                sellerOrderAdapter.setListProduct(it)
                sellerOrderAdapter.notifyDataSetChanged()
            }
        }
    }

    fun sellerResponse(id:Int){
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
                    // set message of alert dialog
                    dialogBuilder.setMessage("Terima penawaran?")
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                            userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
                                viewModelProduct.patchSellerOrder(it, id, "accepted")
                            }
                        })
                        // negative button text and action
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                            userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
                                viewModelProduct.patchSellerOrder(it, id, "declined")
                            }

                        })
                        .setNeutralButton("Close", DialogInterface.OnClickListener {
                                dialog, id -> dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("Penawaran")
                    // show alert dialog
                    alert.show()





    }




}