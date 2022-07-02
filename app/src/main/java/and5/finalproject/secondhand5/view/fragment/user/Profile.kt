package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.auth.UpdateUserBody
import and5.finalproject.secondhand5.view.custom.CustomToast
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.fragment_add_product2.*
import kotlinx.android.synthetic.main.fragment_add_product2.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class Profile : Fragment() {

    lateinit var userManager: UserManager
    lateinit var token : String
    lateinit var email : String
    lateinit var password : String
    lateinit var sizeCheck: String
    var typeCheck : String? = null
    lateinit var imageCheck : String
    lateinit var image : MultipartBody.Part
    private var customToast : CustomToast = CustomToast()
    lateinit var text: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userManager = UserManager(requireActivity())
        imageCheck=""
        view.btn_back_profile.setOnClickListener {
            activity?.onBackPressed()
        }

        val viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) {
            Log.d("aaaaatoken", it)
            getUserData(it)
            token = it

        }

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {

            it?.let {
                getContent(it)
            }
            if (typeCheck !=null && sizeCheck=="<1mb"){
                view.profile_image.setImageURI(it)
            }
        }

        view.profile_image.setOnClickListener {

        }

        view.addpp.setOnClickListener {
            if (askForPermissions()) {
                getContent.launch("image/*")
            }
        }


        view.btnsimpan.setOnClickListener {
            val updateNama = update_nama.text.toString()
            val updateKota = update_kota.text.toString()
            val updateAlamat = update_alamat.text.toString()
            val updateNomor = update_nomor.text.toString()

            val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

            viewModel.updateUserData(token, updateNama, email, password, updateNomor.toInt(), updateAlamat, image, updateKota)
            responseUpdate()
//            view.findNavController().navigate(R.id.account)
        }


        return view
    }


    fun getUserData(token:String){
        val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModel.getUserData(token)
        viewModel.getUserData.observe(viewLifecycleOwner) {
                Log.d("namaaaa", it.fullName)
                update_nama.setText(it.fullName)
                update_kota.setText(it.city)
                update_alamat.setText(it.address)
                update_nomor.setText(it.phoneNumber.toString())
                email = it.email
                password = it.password

        }
    }

    fun responseUpdate(){
        val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModel.updateUserData.observe(viewLifecycleOwner){ code->
            Log.d("codex", code)
            if (code == "201"){
                Handler(Looper.getMainLooper()).postDelayed({
                    text = "Profile Updated"
                    customToast.successPostToast(requireActivity(), text)

                },2000)


            }else if ( code == "400"){
                Handler(Looper.getMainLooper()).postDelayed({
                    text = "Email already exist"
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
        val getImageSize = requestBody.contentLength().toDouble()
        val convertToMB = getImageSize/1000000
        if (convertToMB > 1 ){
            sizeCheck = ">1mb"
        }else{
            sizeCheck = "<1mb"
        }
        Log.d("imagesize", convertToMB.toString())
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