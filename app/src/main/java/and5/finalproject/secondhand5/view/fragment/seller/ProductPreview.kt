package and5.finalproject.secondhand5.view.fragment.seller

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.custom.CustomToast
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_preview.*
import kotlinx.android.synthetic.main.fragment_product_preview.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import kotlin.properties.Delegates


class ProductPreview : Fragment() {

    lateinit var categoryName : String
    lateinit var image : MultipartBody.Part

    lateinit var postCategory : String
    lateinit var userManager: UserManager
    private var customToast : CustomToast = CustomToast()
    var post by Delegates.notNull<Boolean>()
    lateinit var text : String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_preview, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        post = true
        userManager = UserManager(requireActivity())
        var getProduct = arguments?.getParcelable<AddProduct.productPreview>("product_preview")
        val getCategoryID= getProduct?.selectedID.toString()
        val categoryID = getCategoryID.replace("[","").replace("]", "")
        postCategory = categoryID

        if (getProduct?.selectedName?.size!! <2){
            val getCategoryName = getProduct?.selectedName.toString()
            categoryName = getCategoryName.replace("[","").replace("]", "").replace(",", "")
        }else{
            val getCategoryName = getProduct?.selectedName.toString()
            categoryName = getCategoryName.replace("[","").replace("]", "")
        }
        preview_product_name.text = "Name : ${getProduct?.productName}"
        preview_product_category.text = "Category : $categoryName"
        preview_product_price.text = "Price : ${getProduct?.productPrice}"
        preview_product_description.text =getProduct?.productDesc
        preview_image.setImageURI(getProduct!!.imageParsing.toUri())

        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            detailUser(it)
        }

        btn_post_preview.setOnClickListener {
            postPreview_loading?.visibility = View.VISIBLE
            btn_post_preview.visibility = View.GONE
            observe()
            Handler(Looper.getMainLooper()).postDelayed({
                postPreview_loading?.visibility = View.GONE
                btn_post_preview.visibility = View.VISIBLE
            }, 1500)
            GlobalScope.launch {
                userManager.deletePostImageCache()
                userManager.savePostImageCache("")
            }
        }


    }

    private fun detailUser(token:String) {
        val viewModelUser = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModelUser.getUserData(token)
        viewModelUser.getUserData.observe(viewLifecycleOwner){
            Log.d("namaaaa", it.fullName)
            Log.d("cityyyy", it.city)
            preview_seller_name.setText(it.fullName)
            preview_seller_city.setText(it.city)
            Glide.with(requireActivity()).load( it.imageUrl).into(requireView().preview_seller_image)
        }
    }

    fun observe(){
        var getProduct = arguments?.getParcelable<AddProduct.productPreview>("product_preview")
        val loginViewModel =ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""){
                val userViewModel =ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
                userViewModel.getUserData(token)
                userViewModel.getUserData.observe(viewLifecycleOwner) {  loc ->
                    val productViewModel =ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
                    if (post) {
                        postProduct(
                            token,
                            getProduct?.productName!!,
                            getProduct!!.productDesc,
                            getProduct!!.productPrice,
                            postCategory,
                            loc.city,
                            getProduct!!.image as MultipartBody.Part
                        )
                        post = false
                    }




                    productViewModel.responseCodeAddProduct.observe(viewLifecycleOwner){ code->
                        Log.d("codex", code)
                        if (code == "201"){
                            Handler(Looper.getMainLooper()).postDelayed({
                                text = "Success"
                                customToast.successPostToast(requireActivity(), text)
                                view?.findNavController()?.navigate(R.id.myListProduct)
                            },2000)


                        }else if ( code == "400"){
                            Handler(Looper.getMainLooper()).postDelayed({
                                text = "Cant Post, Max 5 Product"
                                customToast.failurePostToast(requireActivity(), text)
                            },2000)
                        }else if ( code == "403"){
                            Handler(Looper.getMainLooper()).postDelayed({
                                text = "You Are Not Login or Access Token is Wrong"
                                customToast.failurePostToast(requireActivity(), text)
                            },2000)

                        }else if ( code == "500"){
                            Handler(Looper.getMainLooper()).postDelayed({
                                text = "Internal Service Error"
                                customToast.failurePostToast(requireActivity(), text)
                            },2000)

                        }
                    }
                }
            }
        }
    }

    fun postProduct(
        token: String,
        name: String,
        desc: String,
        price: String,
        category: String,
        location: String,
        image: MultipartBody.Part
    ){
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.addSellerProduct(token, name,desc, price, category, location, image )
    }



}