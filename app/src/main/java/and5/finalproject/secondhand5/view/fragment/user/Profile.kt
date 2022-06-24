package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.auth.UpdateUserBody
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class Profile : Fragment() {
    lateinit var userBody: UpdateUserBody
    lateinit var userManager: UserManager
    lateinit var token : String
    lateinit var email : String
    lateinit var password : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userManager = UserManager(requireActivity())

        view.btn_back_profile.setOnClickListener{
            activity?.onBackPressed()
        }

        val viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) {
            Log.d("aaaaatoken", it)
            getUserData(it)
            token = it

        }

        view.btnsimpan.setOnClickListener {
            val updateNama = update_nama.text.toString()
            val updateKota = update_kota.text.toString()
            val updateAlamat = update_alamat.text.toString()
            val updataNomor = update_nomor.text.toString()

            userBody = UpdateUserBody(updateAlamat, updateKota, email, updateNama, "",  password, updataNomor.toLong() )
            updateUserData(token, userBody)

            view.findNavController().navigate(R.id.account)
        }


        return view
    }

//    @SerializedName("address")
//    @SerializedName("city")
//    @SerializedName("createdAt")
//    @SerializedName("email")
//    @SerializedName("full_name")
//    @SerializedName("image_url")
//    @SerializedName("password")
//    @SerializedName("phone_number")
//    @SerializedName("updatedAt")

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

    fun updateUserData(token:String, user : UpdateUserBody){
        val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModel.updateUserData(token, user)
    }

}