package and5.finalproject.secondhand5.view.fragment.seller

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_order.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class DetailOrder : Fragment() {

    private var orderId by Delegates.notNull<Int>()
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

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }

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

    private fun acceptDialogue(id:Int){
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        dialogBuilder.setMessage("Terima penawaran?")
            .setCancelable(false)
            .setPositiveButton("Ya") { _: DialogInterface, _: Int ->
                acceptOrder(id)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()

            }

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Penawaran")
        // show alert dialog
        alert.show()


    }

    private fun declineDialogue(id:Int){
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        dialogBuilder.setMessage("Tolak penawaran?")
            .setCancelable(false)
            .setPositiveButton("Ya") { _: DialogInterface, _: Int ->
                declineOrder(id)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()

            }

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Penawaran")
        // show alert dialog
        alert.show()


    }

    private fun acceptOrder(id:Int){
//        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
//        userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
//            viewModelProduct.patchSellerOrder(it, id, "accepted")
//        }

        val data = Bundle()
        data.putInt("order_id", orderId)

        findNavController().navigate(R.id.statusOrder, data)

    }

    private fun declineOrder(id:Int){
        val viewModelProduct = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
            viewModelProduct.patchSellerOrder(it, id, "declined")
        }
        findNavController().navigate(R.id.myListProduct)

    }


    private fun orderValueInit(id:Int){
        val viewmodelproduct = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        userManager.userToken.asLiveData().observe(viewLifecycleOwner) { token ->
            viewmodelproduct.getDetailOrder(token, id)
            viewmodelproduct.detailOrder.observe(viewLifecycleOwner) {
                product_name.text = it.product.name
                Glide.with(requireContext()).load(it.product.imageUrl).into(product_image)

                bid_price.text = "Ditawar Rp ${it.price}"
                product_price.text = "Rp ${it.product.basePrice}"

                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                val date = formatter.parse(it.createdAt)
                if (date != null) {
                    order_date.text = date.toString()
                }

                Log.d("testes tgl", it.createdAt)

                buyer_address_city.text = it.user.city
                buyer_name.text = it.user.fullName
                Glide.with(requireContext()).load(it.user.imageURL).into(buyer_image)


            }

        }
    }



}