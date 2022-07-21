package and5.finalproject.secondhand5.view.fragment.buyer

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.buyerproduct.GetBuyerOrderItem
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.wishlist.GetWishlistProductItem
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import and5.finalproject.secondhand5.viewmodel.WishlistViewModel
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.custom_buyer_offer_price.view.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.fragment_wishlist.*
import kotlin.math.log
import kotlin.properties.Delegates


class ProductDetail : Fragment() {

    lateinit var userManager: UserManager
    lateinit var productName : String
    var productPrice by Delegates.notNull<Int>()
    lateinit var productImage : String
    var productId by Delegates.notNull<Int>()
    lateinit var productDescription : String
    lateinit var sellerLocation : String
    var getDetailCategory = mutableSetOf<String>()
    lateinit var sellerName : String


    var orderId = 0

    lateinit var dataOrder : List<GetBuyerOrderItem>
    lateinit var dataWishlist : List<GetWishlistProductItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_product_detail, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userManager = UserManager(requireActivity())
        btn_back_detail_product.setOnClickListener {
//            val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
//            viewModelProduct.clearBuyerDetailProductLiveData()
            activity?.onBackPressed()
        }



        productId = arguments?.getInt("product_id") ?:
        Log.d("testes 1 id ", id.toString())

        Handler(Looper.getMainLooper()).postDelayed({
            val loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
            loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
                if (token!= ""){
                    getOrderData()
                    getWishlistData()
                    addProductToWishlist()
                    getDetailProduct()

                }else{
                    initNoLogin()
                }
            }
        },500)

    }



    fun getOrderData(){

        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            if(it!=null){
                viewModelProduct.buyerOrder.observe(viewLifecycleOwner,{
                    if(it!=null){
                        dataOrder = it
//                        Log.d("testes 1 IT ", it.toString())
                    }

                })

                viewModelProduct.getAllBuyerOrder(it)

            }
        }
    }

    fun getWishlistData() {
        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        val viewModelWishlist =
            ViewModelProvider(requireActivity()).get(WishlistViewModel::class.java)

        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) {
            if (it != null) {
                viewModelWishlist.wishlistProduct.observe(viewLifecycleOwner, {
                    if (it != null) {
                        dataWishlist = it
//                        Log.d("testes", dataWishlist[0].id.toString())
                    }
                })

                viewModelWishlist.getAllWishlistProduct(it)

            }

        }
    }


    fun initNoLogin(){
//        Log.d("testes 2 id ", id.toString())
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.getBuyerDetailProduct(productId)

        viewModelProduct.detailProduct.observe(viewLifecycleOwner) {
//            Log.d("testes 3 id ", id.toString())
            var flag = 0

            if (flag == 0) {
                buy_btn.setClickable(false);
                buy_btn.setText("Login Untuk Order")
            }

            product_name.setText(it.name)
            product_price.setText("Rp ${it.basePrice.toString()}")
            it.categories.forEach {

               getDetailCategory.add(it.name)
                val listToString = getDetailCategory.toString()
                Log.d("detailCategory", listToString)
                val getCategory = listToString.replace("[", "").replace("]","")
                product_category.text = getCategory
            }


            seller_name.setText("${it.user.fullName}")
            seller_address.setText("${it.user.city}")
            Glide.with(requireContext()).load(it.user.imageUrl).into(seller_image)
            product_description.setText("${it.description}")

            productName = it.name
            productPrice = it.basePrice
            if(it.imageUrl !=null){
                productImage = it.imageUrl
            }else{
                productImage = ""
            }

            productDescription = it.description
            sellerLocation = it.location

            Glide.with(requireContext()).load(it.imageUrl).into(product_image)


        }

    }


    fun getDetailProduct(){
//        Log.d("testes 2 id ", id.toString())
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.getBuyerDetailProduct(productId)

        viewModelProduct.detailProduct.observe(viewLifecycleOwner) {
//            Log.d("testes 3 id ", id.toString())


            Handler(Looper.getMainLooper()).postDelayed({
                var flag = 2
                for (i in dataOrder.indices) {
                    if (it.id == dataOrder[i].productId && dataOrder[i].status != "declined") {
                        flag = 0
                        break
                    } else if (it.id == dataOrder[i].productId && dataOrder[i].status == "declined") {
                        orderId = dataOrder[i].id
                        flag = 1
                        break
                    } else {
                        flag = 2
                    }
                    Log.d("it id ", it.id.toString())
                    Log.d("dataOrder id ", dataOrder[i].productId.toString())

                }
                Log.d("testes id ", orderId.toString())

                if (flag == 0) {
                    buy_btn.setClickable(false);
                    buy_btn.setText("Menunggu Respon Penjual")
                } else if (flag == 1) {
                    Log.d("testes id ", "massssssss")
                    reBidProduct()
                } else {
                    Log.d("testes id ", "siiiiiiiiiiiiii")
                    buy_btn.setText("Saya Tertarik dan Ingin Nego")
                    bidProduct()
                }


                productName = it.name
                productPrice = it.basePrice
                if(it.imageUrl != null){
                    productImage = it.imageUrl
                }else{
                    productImage = ""
                }
                productDescription = it.description
                sellerLocation = it.user.city
                sellerName = it.user.fullName

                ////////////


                product_name.setText(productName)
                product_price.setText("Rp ${productPrice.toString()}")
                getDetailCategory.clear()
                it.categories.forEach {

                    getDetailCategory.add(it.name)
                    val listToString = getDetailCategory.toString()
                    Log.d("detailCategory", listToString)
                    val getCategory = listToString.replace("[", "").replace("]","")
                    product_category.text = getCategory
                }

                seller_name.setText("${it.user.fullName}")
                seller_address.setText("$sellerLocation")

                //produk image
                if(productImage != "") {
                    Glide.with(requireContext()).load(productImage).into(product_image)
                }

                product_description.setText("${it.description}")

                //seller image
                if(it.imageUrl != null){
                    Glide.with(requireContext()).load(it.user.imageUrl).into(seller_image)
                }else{
                    it.user.imageUrl == ""
                }


            },200)

        }

    }


    fun bidProduct() {

        buy_btn.setOnClickListener{
            val customOrderDialog = LayoutInflater.from(requireContext()).inflate(R.layout.custom_buyer_offer_price, null, false)

            customOrderDialog.product_name.setText(productName.toString())
            customOrderDialog.product_price.setText("Rp. ${productPrice.toString()}")
            if(productImage != ""){
                Glide.with(requireContext()).load(productImage).into(customOrderDialog.product_image)
            }

            val ADBuilder = AlertDialog.Builder(requireContext())
                .setView(customOrderDialog)
                .create()
            ADBuilder.show()
            customOrderDialog.btn_submit_offer_price.setOnClickListener {
                if (customOrderDialog.input_offer_price.text.isNotEmpty()){
                    var offerPrice = customOrderDialog.input_offer_price.text.toString().toInt()

                    if(offerPrice > productPrice ){
                        Toast.makeText(requireContext(), "Harga Tawar tidak bisa lebih dari harga dasar", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
                        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
                        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
                            if(it!=null){
                                Log.d("testes token", it)
                                viewModelProduct.responseCodeAddBuyerOrder.observe(viewLifecycleOwner) {
//                                    Log.d("tes response ", it.toString())
                                    if (it == "201") {
                                        Toast.makeText(
                                            requireContext(),
                                            "Harga Tawarmu Berhasil dikirim ke penjual",
                                            Toast.LENGTH_SHORT

                                        ).show()
                                        buy_btn.setClickable(false);
                                        buy_btn.setText("Menunggu Respon Penjual")

                                    } else if (it == "400") {
                                        Toast.makeText(
                                            requireContext(),
                                            "\t\n" +
                                                    "you has order for this product",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (it == "403") {
                                        Toast.makeText(
                                            requireContext(),
                                            "\t\n" +
                                                    "You are not login/access_token is wrong",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (it == "500") {
                                        Toast.makeText(
                                            requireContext(), "\t\n" +
                                                    "\t\n" +
                                                    "Internal Service Error", Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            requireContext(), "\t\n" +
                                                    "No Internet Connection", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                viewModelProduct.postBuyerOrder(it, productId, offerPrice)

                            }

                        }

                    }
                    ADBuilder.dismiss()


                }
            }



        }

    }


    fun reBidProduct(){
        buy_btn.setOnClickListener{
            val customOrderDialog = LayoutInflater.from(requireContext()).inflate(R.layout.custom_buyer_offer_price, null, false)

            customOrderDialog.product_name.setText(productName.toString())
            customOrderDialog.product_price.setText("Rp. ${productPrice.toString()}")
            if(productImage != ""){
                Glide.with(requireContext()).load(productImage).into(customOrderDialog.product_image)

            }

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
                                viewModelProduct.responseCodeUpdateBuyerOrder.observe(viewLifecycleOwner,{
//                                    Log.d("tes response ", it.toString())
                                    if(it == "201"){
                                        Toast.makeText(requireContext(), "Harga Tawarmu Berhasil dikirim ke penjual", Toast.LENGTH_SHORT).show()
                                        buy_btn.setClickable(false);
                                        buy_btn.setText("Menunggu Respon Penjual")
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

                                viewModelProduct.updateBuyerOrder(it, orderId, offerPrice)

                            }

                        }

                    }
                    ADBuilder.dismiss()

                }
            }
            ADBuilder.show()


        }

    }

    fun postProductToWishlist(
        token : String,
        id : Int
    ) {
        val viewModelWishlist = ViewModelProvider(requireActivity()).get(WishlistViewModel::class.java)
        viewModelWishlist.postProductToWishlist(token, id)

    }

    fun addProductToWishlist() {
        add_to_wishlist.setOnClickListener {
            val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
            val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

            viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){ token ->
                if(token != null){
                    viewModelProduct.detailProduct.observe(viewLifecycleOwner) {

                        if(dataWishlist.size > 0){

                            var flagWishlist = 2
                            var loop = 0
                            for (i in dataWishlist.indices){
                                Log.d("testes loop ke", i.toString())
                                Log.d("testes id api", it.id.toString())
                                Log.d("testes id data", dataWishlist[i].product.id.toString())

                                if(it.id == dataWishlist[i].product.id) {
                                    flagWishlist = 0
                                    Log.d("testes wish 1", flagWishlist.toString())
                                    break
                                }else {
                                    flagWishlist = 1
                                    Log.d("testes wish 2", flagWishlist.toString())
                                }
                                Log.d("testes wish for", flagWishlist.toString())
                                loop++
                            }

                            Log.d("testes wish", flagWishlist.toString())

                            if(flagWishlist==1){
                                postProductToWishlist(token, it.id)
                                Toast.makeText(requireContext(), "Barang berhasil masuk ke wishlist", Toast.LENGTH_SHORT).show()
                                view?.findNavController()?.navigate(R.id.productDetail)
                            }else if(flagWishlist==0){
//                                Toast.makeText(requireContext(), "Barang sudah masuk ke wishlist anda", Toast.LENGTH_SHORT).show()

                                val dialogBuilder = AlertDialog.Builder(requireActivity())
                                dialogBuilder.setMessage("Hapus Dari Wishlist?")
                                    .setCancelable(false)
                                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                                        deleteWishlist(dataWishlist[loop].id)
                                    })
                                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, id ->
                                        dialog.cancel()

                                    })


                                val alert = dialogBuilder.create()
                                alert.setTitle("Wishlist")
                                alert.show()


                            }else{
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()

                            }
                        }else{
                            Toast.makeText(requireContext(), "Barang berhasil masuk ke wishlist", Toast.LENGTH_SHORT).show()
                            postProductToWishlist(token, it.id)
                            view?.findNavController()?.navigate(R.id.productDetail)

                        }

                    }

                }


            }
        }
    }

    private fun deleteWishlist(wishlistId : Int) {

        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        val viewModelWishlist = ViewModelProvider(requireActivity()).get(WishlistViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            viewModelWishlist.deleteWishlistProduct(it, wishlistId)
        }

        Toast.makeText(requireContext(), "Barang berhasil dihapus dari wishlist", Toast.LENGTH_SHORT).show()

    }



}