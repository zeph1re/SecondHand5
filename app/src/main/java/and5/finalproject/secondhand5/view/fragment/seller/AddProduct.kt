package and5.finalproject.secondhand5.view.fragment.seller

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_add_product2.view.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.sql.Types.NULL

class AddProduct : Fragment() {

    lateinit var userManager: UserManager
    private val selectedName: MutableList<String?> = mutableListOf()
    private var selectedID: MutableList<Int> = mutableListOf()
    var categoryName = mutableListOf<String>()
    var categoryID = mutableListOf<Int>()
    lateinit var image : MultipartBody.Part
    lateinit var uri : Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userManager = UserManager(requireActivity())

        val view = inflater.inflate(R.layout.fragment_add_product2, container, false)

        dropdownAdapter()

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
            view.add_product_image.setImageURI(it)
            it?.let {
                getContent(it)
            }
        }

        view.add_product_image.setOnClickListener {
            if (askForPermissions()) {
                getContent.launch("image/*")
            }
        }

        view.add_btn.setOnClickListener {
            val productName = view.add_product_name.text.toString()
            val productPrice = view.add_product_price.text.toString().toInt()
            val productDesc = view.add_product_desc.text.toString()

            val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
            viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
                if (it != ""){
                    Log.d("newtoken", it)
                    postProduct(it, productName, productDesc, productPrice, "31", "Jakarta", image)
                }
            }
        }

    return view

    }

    fun postProduct(token: String, name: String, desc: String, price: Int, category: String, location: String, image: MultipartBody.Part){
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.addSellerProduct(token, name,desc, price, category, location, image )
    }

    fun getContent(it : Uri){
        val contentResolver = requireActivity().contentResolver
        val type = contentResolver.getType(it)
        val tempFile = File.createTempFile("temp-", null, null)
        val inputStream = contentResolver.openInputStream(it)
        tempFile.outputStream().use {
            inputStream?.copyTo(it)
        }
        val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
        image =
            MultipartBody.Part.createFormData("Image", tempFile.name, requestBody)
        Log.d("aset", image.toString())
    }

    fun dropdownAdapter(){
        view?.dropdown_category?.hint = "Pilih Kategori"
        view?.dropdown_category?.inputType=NULL
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.sellerCategory.observe(viewLifecycleOwner) {
            it.forEach {
                categoryName.add(it.name)
                categoryID.add(it.id)
            }
        }
        viewModelProduct.getSellerCategory()
        val categoryAdapter = ArrayAdapter(requireActivity(), R.layout.adapter_pilih_kategory, categoryName)
        view?.dropdown_category?.setAdapter(categoryAdapter)

        view?.dropdown_category?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        view?.dropdown_category?.setOnItemClickListener { adapterView, view, position, l ->
            val selectedValue: String? = categoryAdapter.getItem(position)

            selectedName.add(categoryAdapter.getItem(position))
            selectedID.add(categoryID[position])
            Log.d("asdd", selectedID.toString())

            categoryName.remove(selectedValue)
            categoryID.remove(categoryID[position])
        }
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
                    val uri = Uri.fromParts("package", "com.binar.challengechapterenam", null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }

}








