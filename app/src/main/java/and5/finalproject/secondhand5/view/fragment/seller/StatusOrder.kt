@file:Suppress("ControlFlowWithEmptyBody", "ControlFlowWithEmptyBody", "ControlFlowWithEmptyBody",
    "LiftReturnOrAssignment"
)

package and5.finalproject.secondhand5.view.fragment.seller

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.custom_seller_status.view.*
import kotlinx.android.synthetic.main.custom_seller_whastapp.view.*
import kotlinx.android.synthetic.main.fragment_status_order.*
import kotlinx.android.synthetic.main.home_product_adapter.view.product_image
import kotlinx.android.synthetic.main.home_product_adapter.view.product_name
import kotlinx.android.synthetic.main.home_product_adapter.view.product_price
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class StatusOrder : Fragment() {

    private var orderId by Delegates.notNull<Int>()

    lateinit var productName :String
    private lateinit var productImage :String
    private var productPrice by Delegates.notNull<Int>()
    private var bidPrice by Delegates.notNull<Int>()

    private lateinit var buyerName :String
    private lateinit var buyerCity :String
    private lateinit var buyerAddress :String
    private lateinit var buyerImage :String
    private lateinit var buyerPhoneNumber :String

    lateinit var userManager: UserManager

    private var submit: Button? = null
    private var acceptRadio: RadioButton? = null
    private var declineRadio: RadioButton? = null
    private lateinit var orderDate : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }


        orderId = requireArguments().getInt("order_id")

        orderValueInit(orderId)

        Handler(Looper.getMainLooper()).postDelayed({
            dialogWA()
            statusDialog()
            callWA()

            buyer_name.text = buyerName
            buyer_address_city.text = buyerCity
            if(buyerImage!="kosong"){
                Glide.with(requireContext()).load(buyerImage).into(buyer_image)
            }else{
                buyerImage = ""
            }

            product_name.text = productName
            product_price.text = "Rp $productPrice"
            bid_price.text = "Ditawar Rp $bidPrice"
            Glide.with(requireContext()).load(productImage).into(product_image)

            val formatter =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val date = formatter.parse(orderDate)
            if (date != null) {
                order_date.text = date.toString()
            }
        },500)


    }



    private fun acceptOrder(id:Int){

//        val data = Bundle()
//        data.putInt("order_id", orderId)

        val viewModelProduct = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

        val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            viewModelProduct.patchSellerOrder(token, id, "accepted")
            findNavController().navigate(R.id.myListProduct)

        }
//        findNavController().navigate(R.id.myListProduct)

    }

    private fun declineOrder(id:Int){
        val viewModelProduct = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

        val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            viewModelProduct.patchSellerOrder(token, id, "declined")
            findNavController().navigate(R.id.myListProduct)

        }


    }

    private fun callWA() {

        btn_call.setOnClickListener {
            dialogWA()
        }


    }

    private fun statusDialog(){
        btn_status.setOnClickListener {
            val customStatus = LayoutInflater.from(requireContext()).inflate(R.layout.custom_seller_status, null, false)

            submit = customStatus.btn_submit
            acceptRadio = customStatus.radio_btn_gas
            declineRadio =  customStatus.radio_btn_cancel

            val aDBuilder = AlertDialog.Builder(requireContext())
                .setView(customStatus)
                .create()

            customStatus.btn_submit.setOnClickListener {

                if(acceptRadio!!.isChecked){
                    Log.d("testes", "aseppppppp")
                    acceptOrder(orderId)
                    aDBuilder.dismiss()
                }else if(declineRadio!!.isChecked){
                    Log.d("testes", "deklinnnnnn")
                    declineOrder(orderId)
                    aDBuilder.dismiss()
                }else{
                    Toast.makeText(requireContext(), "Mohon Pilih Satu", Toast.LENGTH_SHORT).show()

                }
                aDBuilder.dismiss()

            }



            aDBuilder.show()

        }
    }

    private fun dialogWA(){
        val customWaDialog = LayoutInflater.from(requireContext()).inflate(R.layout.custom_seller_whastapp, null, false)

        customWaDialog.product_name.text = productName
        Glide.with(requireContext()).load(productImage).into(customWaDialog.product_image)
        customWaDialog.product_bid_price.text = "Ditawar Rp $bidPrice"
        customWaDialog.product_price.text = "Rp $productPrice"
        customWaDialog.buyer_phone_number.text = "WA : $buyerPhoneNumber"
        customWaDialog.buyer_address_city.text = buyerCity
        customWaDialog.buyer_name.text = buyerName


        if(buyerImage!="kosong"){
            Glide.with(requireContext()).load(buyerImage).into(customWaDialog.buyer_image)
        }


        customWaDialog.btn_call_whatsapp.setOnClickListener {
            if(buyerPhoneNumber.startsWith("+62") || buyerPhoneNumber.startsWith("62")){
//                var phoneNumber = "62"
                val message = "Selamat! Barang $productName berhasil ditawar menjadi ${productPrice}. Apakah kamu ingin melanjutkan pembelian ini? Konfirmasi alamat pemesanan $buyerAddress"
                val url = "https://api.whatsapp.com/send?phone=$buyerPhoneNumber"+"&text=" + URLEncoder.encode(message, "UTF-8")
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }else{
                Toast.makeText(requireContext(), "Nomor Tidak Terdaftar", Toast.LENGTH_SHORT).show()
            }

        }

        val aDBuilder = AlertDialog.Builder(requireContext())
            .setView(customWaDialog)
            .create()

        aDBuilder.show()
    }

    private fun orderValueInit(id:Int){
        val viewmodelproduct = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

        val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""){
                viewmodelproduct.getDetailOrder(token, id)

                viewmodelproduct.detailOrder.observe(viewLifecycleOwner) {

                    productName = it.product.name
                    productImage = it.product.imageUrl
                    productPrice = it.product.basePrice
                    bidPrice = it.price
                    orderDate = it.createdAt

                    buyerCity = it.user.city
                    buyerName = it.user.fullName
                    buyerPhoneNumber = it.user.phoneNumber
                    buyerAddress = it.user.address
                    if(it.user.imageURL != null ){
                        buyerImage = it.user.imageURL
                    }else{
                        buyerImage = "kosong"
                    }


                }
            }else{

            }
        }


    }

}