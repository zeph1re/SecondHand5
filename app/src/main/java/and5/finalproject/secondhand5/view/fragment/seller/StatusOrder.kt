package and5.finalproject.secondhand5.view.fragment.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.app.AlertDialog
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.custom_seller_whastapp.view.*
import kotlinx.android.synthetic.main.fragment_status_order.*
import kotlinx.android.synthetic.main.home_product_adapter.view.product_image
import kotlinx.android.synthetic.main.home_product_adapter.view.product_name
import kotlinx.android.synthetic.main.home_product_adapter.view.product_price
import kotlin.properties.Delegates


class StatusOrder : Fragment() {

    var orderId by Delegates.notNull<Int>()

    lateinit var productName :String
    lateinit var productImage :String
    var productPrice by Delegates.notNull<Int>()
    var bidPrice by Delegates.notNull<Int>()

    lateinit var buyerName :String
    lateinit var buyerAddress :String
    lateinit var buyerImage :String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderId = requireArguments().getInt("order_id")

        orderValueInit(orderId)

        Handler(Looper.getMainLooper()).postDelayed({
            dialogWA()

            buyer_name.text = buyerName
            buyer_address_city.text = buyerAddress
            product_name.text = productName
            product_price.text = "Rp ${productPrice.toString()}"
            bid_price.text = "Ditawar Rp ${bidPrice.toString()}"
            Glide.with(requireContext()).load(productImage).into(product_image)
        },1000)


    }

    fun dialogWA(){
        val customWaDialog = LayoutInflater.from(requireContext()).inflate(R.layout.custom_seller_whastapp, null, false)

        customWaDialog.product_name.text = productName
        Glide.with(requireContext()).load(productImage).into(customWaDialog.product_image)
        customWaDialog.product_bid_price.text = "Ditawar Rp ${bidPrice.toString()}"
        customWaDialog.product_price.text = "Rp ${productPrice.toString()}"


        customWaDialog.buyer_address_city.text = buyerAddress
        customWaDialog.buyer_name.text = buyerName

        val ADBuilder = AlertDialog.Builder(requireContext())
            .setView(customWaDialog)
            .create()

        ADBuilder.show()
    }

    fun orderValueInit(id:Int){
        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        val loginViewModel =ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""){
                viewmodelproduct.getDetailOrder(token, id)

                viewmodelproduct.detailOrder.observe(viewLifecycleOwner,{

                    productName = it.product.name
                    productImage = it.product.imageUrl
                    productPrice = it.product.basePrice
                    bidPrice = it.price

                    buyerAddress = it.user.address
                    buyerName = it.user.fullName



                })
            }else{

            }
        }


    }

}