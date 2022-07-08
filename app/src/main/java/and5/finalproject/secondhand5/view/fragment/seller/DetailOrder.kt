package and5.finalproject.secondhand5.view.fragment.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_order.*
import kotlin.properties.Delegates


class DetailOrder : Fragment() {

    var orderId by Delegates.notNull<Int>()
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this frament
        return inflater.inflate(R.layout.fragment_detail_order, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireActivity())

        orderId = requireArguments().getInt("order_id")

        orderValueInit(orderId)
        btn_accept.setOnClickListener {
            acceptDialogue(orderId)
        }

        btn_decline.setOnClickListener {
            declineDialogue(orderId)
        }
    }

    fun acceptDialogue(id:Int){
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        dialogBuilder.setMessage("Terima penawaran?")
            .setCancelable(false)
            .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                acceptOrder(id)
            })
            .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()

            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Penawaran")
        // show alert dialog
        alert.show()


    }

    fun declineDialogue(id:Int){
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        dialogBuilder.setMessage("Tolak penawaran?")
            .setCancelable(false)
            .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                declineOrder(id)
            })
            .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()

            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Penawaran")
        // show alert dialog
        alert.show()


    }

    fun acceptOrder(id:Int){
//        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
//        userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
//            viewModelProduct.patchSellerOrder(it, id, "accepted")
//        }

        val data = Bundle()
        data.putInt("order_id", orderId)

        findNavController().navigate(R.id.statusOrder, data)

    }

    fun declineOrder(id:Int){
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
            viewModelProduct.patchSellerOrder(it, id, "declined")
        }
        findNavController().navigate(R.id.myListProduct)

    }


    fun orderValueInit(id:Int){
        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
            viewmodelproduct.getDetailOrder(it, id)

            viewmodelproduct.detailOrder.observe(viewLifecycleOwner,{

                product_name.text = it.product.name
                Glide.with(requireContext()).load(it.product.imageUrl).into(product_image)
                bid_price.text = "Ditawar Rp ${it.price}"
                product_price.text = "Rp ${it.product.basePrice.toString()}"

                if(it.transactionDate != null){
                    order_date.text = it.transactionDate.toString()
                }
                buyer_address_city.text = it.user.city
                buyer_name.text = it.user.fullName

            })

        }
    }

}