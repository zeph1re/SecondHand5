package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.custom.CustomToast
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
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
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.custom_zoom_photo_profile_dialog.view.*
import kotlinx.android.synthetic.main.fragment_add_product2.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class Profile : Fragment() {

    lateinit var userManager: UserManager
    lateinit var token : String
    lateinit var email : String
    lateinit var password : String
    lateinit var sizeCheck: String
    var typeCheck : String? = null
    lateinit var imageCheck : String
    var image : MultipartBody.Part? = null
    private var customToast : CustomToast = CustomToast()
    lateinit var text: String
    lateinit var getProfileImage : String
    lateinit var zoomType : String
    lateinit var localZoom : Uri
    lateinit var sendNoImage : String
    lateinit var updateNama : String
    lateinit var updateKota : String
    lateinit var updateAlamat : String
    lateinit var updateNomor: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userManager = UserManager(requireActivity())
        imageCheck=""
        zoomType = "online"

        sendNoImage = "true"


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
                sendNoImage = "false"
                zoomType = "local"
                localZoom = it
            }else{
                zoomType = "online"
            }
        }




        val city =  resources.getStringArray(R.array.city)
        val sortedAlphabet = city.sorted()

        view?.update_kota?.setInputType(InputType.TYPE_NULL)

        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.adapter_city, sortedAlphabet)
        view?.update_kota?.setAdapter(arrayAdapter)
        view?. update_kota?.setOnItemClickListener { _, view, position, l ->
            val selectedValue: String? = arrayAdapter.getItem(position)
            view?.update_kota?.setText(selectedValue, false)
        }

        view.profile_image.setOnClickListener {
            view?.update_kota?.setText("")
            val zoomPhoto = LayoutInflater.from(requireContext()).inflate(R.layout.custom_zoom_photo_profile_dialog, null, false)
            val ADBuilder = android.app.AlertDialog.Builder(requireContext())
                .setView(zoomPhoto)
                .create()
            ADBuilder.show()
            zoomPhoto.delete_profile_pic.setOnClickListener {
                profile_image.setImageResource(R.drawable.pp)
                zoomPhoto.zoom_pp.setImageResource(R.drawable.pp)
                getProfileImage = ""
                localZoom = "".toUri()
                zoomType = "blank"
                sendNoImage = "true"

            }

            if (zoomType == "local"){
                Glide.with(requireActivity()).load(localZoom).into(zoomPhoto.zoom_pp)
            }else if (zoomType == "online"){
                if (getProfileImage == "blank"){
                    zoomPhoto.zoom_pp.setImageResource(R.drawable.pp)
                }else{
                    Glide.with(requireActivity()).load(getProfileImage).into(zoomPhoto.zoom_pp)
                }


            }else{
                zoomPhoto.zoom_pp.setImageResource(R.drawable.pp)
            }




        }

        view.addpp.setOnClickListener {
            if (askForPermissions()) {
                getContent.launch("image/*")
            }
        }


        view.btnsimpan.setOnClickListener {
            updateNama = update_nama.text.toString()
            updateKota = update_kota.text.toString()
            updateAlamat = update_alamat.text.toString()
            updateNomor = update_nomor.text.toString()
            check()
            val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
            if (sendNoImage == "true"){
                val attachmentEmpty = RequestBody.create("text/plain".toMediaTypeOrNull(), "")
                image  =    MultipartBody.Part.createFormData("image", "", attachmentEmpty)
                Log.d("tesblank", image.toString())
            }
                if (updateNama.isNotEmpty() && updateKota.isNotEmpty() && updateAlamat.isNotEmpty() && updateNomor.isNotEmpty()) {
                    if (updateNomor.startsWith("+62") || updateNomor.startsWith("62")){
                        viewModel.updateUserData(
                            token, updateNama, email, "", updateNomor, updateAlamat,
                            image!!, updateKota
                        )
                    }else{
                        Toast.makeText(requireContext(), "Nomor Harus Dari +62 (Indonesia)", Toast.LENGTH_SHORT).show()
                    }

                }

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
                update_kota.setText(it.city, false)






                update_alamat.setText(it.address)
                val phone = it.phoneNumber
                update_nomor.setText("+$phone")
                if (it.imageUrl != null){
                    Glide.with(requireActivity()).load( it.imageUrl).into(view!!.profile_image)
                }else{
                    profile_image.setImageResource(R.drawable.pp)
                }

                val getImage = it.imageUrl
                if (it.imageUrl!=null){
                    getProfileImage = it.imageUrl.toString()
                }else{
                    getProfileImage = "blank"
                }

                image  =   MultipartBody.Part.createFormData("image", getImage.toString())
                email = it.email

                password = it.password
                Log.d ("pass", password)

        }
    }

    fun responseUpdate(){
        val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModel.updateUserData.observe(viewLifecycleOwner){ code->
            Log.d("codex", code)
            if (code == "200"){
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

    fun check(){
        if (updateNama.isEmpty()){
            field_name.helperText = "Required"
            update_nama.error = "Name cannot be empty"
        } else{
            field_name.helperText  = ""
        }

        if (updateKota.isEmpty()){
            field_city.helperText = "Required"
            update_kota.error = "City cannot be empty"
        } else {
            field_city.helperText = ""
        }

        if (updateAlamat.isEmpty()){
            field_address.helperText = "Required"
            update_alamat.error = "Address cannot be empty"
        } else {
            field_address.helperText  = ""
        }

        if (updateNomor.isEmpty()){
            field_nomor.helperText = "Required"
            update_nomor.error = "Phone number cannot be empty"
        } else {
            field_nomor.helperText = ""
        }



    }

}