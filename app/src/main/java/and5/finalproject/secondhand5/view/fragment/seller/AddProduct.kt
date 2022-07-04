package and5.finalproject.secondhand5.view.fragment.seller

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.custom.CustomToast
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_product2.*
import kotlinx.android.synthetic.main.fragment_add_product2.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlin.properties.Delegates


class AddProduct : Fragment() {

    lateinit var userManager: UserManager
    private val selectedName: MutableList<String?> = mutableListOf()
    private var selectedID: MutableList<Int> = mutableListOf()
    lateinit var postCategory : String
    var categoryID = mutableListOf<Int>()
    var categoryName = mutableListOf<String>()
    lateinit var image : MultipartBody.Part
    lateinit var productName : String
    lateinit var productPrice : String
    lateinit var productDesc  : String
    lateinit var imageCheck : String
    lateinit var arrayAdapter: ArrayAdapter<String>
    var typeCheck : String? = null
    private var customToast : CustomToast = CustomToast()
    lateinit var text : String
    var post by Delegates.notNull<Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_add_product2, container, false)
        userManager = UserManager(requireActivity())
        imageCheck = ""


        view?.dropdown_category?.hint = "Select Category"
        getCategory()
        arrayAdapter = ArrayAdapter(requireActivity(), R.layout.adapter_pilih_kategory, categoryName)
        view?.dropdown_category?.setAdapter(arrayAdapter)
        view?.dropdown_category?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        arrayAdapter.notifyDataSetChanged()
        view?.dropdown_category?.setOnItemClickListener { adapterView, view, position, l ->
            val selectedValue: String? = arrayAdapter.getItem(position)
            selectedName.add(arrayAdapter.getItem(position))
            selectedID.add(categoryID[position])
            categoryName.remove(selectedValue)
            categoryID.remove(categoryID[position])
            val getID = selectedID.toString()
            postCategory = getID.replace("[","").replace("]", "")
            Log.d("cateeee", postCategory)
            Log.d("asdd", selectedID.toString())
        }

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
            warning_image.text = ""
            it?.let {
                getContent(it)
            }
            if (typeCheck !=null){
                view.add_product_image.setImageURI(it)
            }

            if (typeCheck == null){
                imageCheck = "false"
                view.warning_image.visibility = View.VISIBLE

                view.warning_image.text = "File format not supported"
            }
        }

        view.add_product_image.setOnClickListener {
            if (askForPermissions()) {
                getContent.launch("image/*")
            }

        }

        view.reset_category.setOnClickListener {
            view.dropdown_category.setText("")
            selectedID.clear()
            categoryName.clear()
            getCategory()
            arrayAdapter = ArrayAdapter(requireActivity(), R.layout.adapter_pilih_kategory, categoryName)
            view?.dropdown_category?.setAdapter(arrayAdapter)
            view?.dropdown_category?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
            view?.dropdown_category?.setOnItemClickListener { adapterView, view, position, l ->
                val selectedValue: String? = arrayAdapter.getItem(position)
                selectedName.add(arrayAdapter.getItem(position))
                selectedID.add(categoryID[position])
                categoryName.remove(selectedValue)
                categoryID.remove(categoryID[position])
                val getID = selectedID.toString()
                postCategory = getID.replace("[","").replace("]", "")
                Log.d("cateeee", postCategory)
                Log.d("asdd", selectedID.toString())
            }

        }


        view.add_btn.setOnClickListener {
            productName = view.add_product_name.text.toString()
            productPrice = view.add_product_price.text.toString()
            productDesc = view.add_product_desc.text.toString()
            check()

            if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDesc.isNotEmpty()
                && text_field_category.isNotEmpty() && imageCheck == "true") {
                view?.post_loading?.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    view?.post_loading?.visibility = View.GONE
                }, 1500)
                post = true
                observe()

            }
        }

    return view

    }

    fun getCategory(){
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.sellerCategory.observe(viewLifecycleOwner) {
            it.forEach {
                categoryName.add(it.name)
                categoryID.add(it.id)
            }
        }
        viewModelProduct.getSellerCategory()
    }
    fun observe(){

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
                                productName,
                                productDesc,
                                productPrice.toInt(),
                                postCategory,
                                loc.city,
                                image
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

    fun check(){
        if (productName.isEmpty()){
            field_product_name.helperText = "Required"
            add_product_name.error = "Product name cannot be empty"
        } else{
            field_product_name.helperText = ""
        }

        if (productPrice.isEmpty()){
            field_product_price.helperText = "Required"
            add_product_price.error = "Product price cannot be empty"
        } else {
            field_product_price.helperText = ""
        }

        val category = dropdown_category.text.toString()
        if (category.isEmpty()){
            text_field_category.helperText = "Required"
            dropdown_category.error = "Product category cannot be empty"
        } else {
            text_field_category.helperText = ""
        }


        if (productDesc.isEmpty()){
            field_product_desc.helperText = "Required"
            add_product_desc.error = "Product description cannot be empty"
        } else {
            field_product_desc.helperText = ""
        }

        if (imageCheck != "true"){
            warning_image.visibility = View.VISIBLE
            warning_image.text = "Please add the image!"
        }

    }

    fun postProduct(
        token: String,
        name: String,
        desc: String,
        price: Int,
        category: String,
        location: String,
        image: MultipartBody.Part
    ){
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.addSellerProduct(token, name,desc, price, category, location, image )
    }

    fun getContent(it : Uri){
        val contentResolver = requireActivity().contentResolver
        val type = contentResolver.getType(it)
        val getType = type.toString()
        if (getType == "image/png"){
            typeCheck = ".png"
        }else if (getType == "image/jpg"){
            typeCheck = ".jpg"
        }else if (getType == "image/jpeg"){
            typeCheck = ".jpeg"
        }else {
            typeCheck = null
        }
        val outputDir = context!!.cacheDir // context being the Activity pointer
        val tempFile = File.createTempFile("temp-", typeCheck, outputDir)
        var customName = tempFile.name.toString()
        val regex = Regex("[0-9]")
        var removeTempName = customName.replace("temp-", "Product Image")
        var postCustomName = regex.replace(removeTempName, "")
        Log.d("logdir", postCustomName.toString())
        val inputStream = contentResolver.openInputStream(it)
        tempFile.outputStream().use {
            inputStream?.copyTo(it)
        }
        val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
        image  =    MultipartBody.Part.createFormData("image", postCustomName, requestBody)
        imageCheck = "true"
        Log.d("cekk", imageCheck)

    }



    fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2000)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestCode) {
            2000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    //  askForPermissions()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", "and5.finalproject.secondhand5", null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }

}








