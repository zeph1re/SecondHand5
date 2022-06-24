package and5.finalproject.secondhand5.view.fragment.buyer

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.custom_buyer_offer_price.view.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlin.properties.Delegates


class ProductDetail : Fragment() {

    lateinit var userManager: UserManager
    lateinit var productName : String
    var productPrice by Delegates.notNull<Int>()
    lateinit var productImage : String
    var productId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireActivity())
        btn_back_detail_product.setOnClickListener {
            activity?.onBackPressed()
        }
//        product_name.setText("tes")
//        product_price.setText("tes")

        productId = arguments?.getInt("product_id") ?:
//        val data = arguments?.getParcelable<GetProductItem>("data") as GetProductItem


//        Toast.makeText( requireContext(), "$id" , Toast.LENGTH_SHORT).show()
        Log.d("testes 1 id ", id.toString())

        init()
        offerProduct()

    }

    fun init(){
//        Log.d("testes 2 id ", id.toString())
        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelproduct.getDetailProduct(productId)

        viewmodelproduct.detailProduct.observe(viewLifecycleOwner,{
//            Log.d("testes 3 id ", id.toString())

            product_name.setText(it.name)
            product_price.setText("Rp ${it.basePrice.toString()}")
            productName = it.name
            productPrice = it.basePrice
            productImage = it.imageUrl

            Glide.with(requireContext()).load(it.imageUrl).into(product_image)
//            Log.d("testes 4 id ", id.toString())

        })

    }

    fun offerProduct() {

        buy_btn.setOnClickListener{
            val customOrderDialog = LayoutInflater.from(requireContext()).inflate(R.layout.custom_buyer_offer_price, null, false)

            customOrderDialog.product_name.setText(productName.toString())
            customOrderDialog.product_price.setText("Rp. ${productPrice.toString()}")
            Glide.with(requireContext()).load(productImage).into(customOrderDialog.product_image)

            val ADBuilder = AlertDialog.Builder(requireContext())
                .setView(customOrderDialog)
                .create()

            customOrderDialog.btn_submit_offer_price.setOnClickListener {
                if (customOrderDialog.input_offer_price.text.isNotEmpty()){
                    var offerPrice = customOrderDialog.input_offer_price.text.toString().toInt()

                    if(offerPrice > productPrice ){
                        Toast.makeText(requireContext(), "Harga Tawar tidak bisa lebih dari harga dasar", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
                        userManager.userToken.asLiveData().observe(viewLifecycleOwner){
                            if(it!=null){
                                Log.d("testes token", it)
                                viewModelProduct.addBuyerOrderLiveData.observe(viewLifecycleOwner,{
                                    Log.d("tes response ", it.toString())
                                    if(it == "201"){
                                        Toast.makeText(requireContext(), "Harga Tawarmu Berhasil dikirim ke penjual", Toast.LENGTH_SHORT).show()
                                    }
                                    else if(it == "400"){
                                        Toast.makeText(requireContext(), "\t\n" +
                                                "you has order for this product", Toast.LENGTH_SHORT).show()
                                    }
                                    else if(it == "403"){
                                        Toast.makeText(requireContext(), "\t\n" +
                                                "You are not login/access_token is wrong", Toast.LENGTH_SHORT).show()
                                    }
                                    else if(it == "500"){
                                        Toast.makeText(requireContext(), "\t\n" +
                                                "\t\n" +
                                                "Internal Service Error", Toast.LENGTH_SHORT).show()
                                    }

                                    else{
                                        Toast.makeText(requireContext(), "\t\n" +
                                                "No Internet Connection", Toast.LENGTH_SHORT).show()

                                    }
                                })

                                viewModelProduct.postBuyerOrder(it, productId, offerPrice)

                            }

                        }

                    }
                    ADBuilder.dismiss()
                }
            }
            ADBuilder.show()


        }
    }

}