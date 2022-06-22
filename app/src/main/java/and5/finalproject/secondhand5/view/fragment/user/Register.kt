package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.custom.CustomToast
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.util.regex.Pattern


class Register : Fragment () {

    private lateinit var inputemail: String
    private lateinit var inputpassword: String
    private lateinit var inputfullname:String
    lateinit var toast: String
    private var customToast : CustomToast = CustomToast()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        view.btntologin.setOnClickListener{
            view.findNavController().navigate(R.id.action_register_to_login)
        }
        view.btndaftar.setOnClickListener {
            inputfullname = regis_nama_lengkap.text.toString()
            inputemail = regis_email.text.toString()
            inputpassword = regis_password.text.toString()

            check()
            if (inputfullname.isNotEmpty() && inputemail.isNotEmpty() && inputpassword.isNotEmpty() && inputpassword.length > 5 && validateEmail(inputemail)){
                view.loading_register.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    view?.loading_register?.visibility = View.GONE
                },2000)
                registerUser(inputfullname, inputemail, inputpassword, 0, "default", "default")
            }

            }

        return view
    }

    fun check(){
        if (inputfullname.isEmpty()){
            field_name.helperText = "Required"
            regis_nama_lengkap.error = "Name cannot be empty"
        }else{
            field_name.helperText = ""
        }

        if (inputemail.isEmpty()){
            field_email.helperText = "Required"
            regis_email.error = "Email cannot be empty"
        }

        if (inputemail.isNotEmpty() && !validateEmail(inputemail)){
            field_email.helperText = "Enter a valid email"
            regis_email.error = "Incorrect email pattern"
        } else if (validateEmail(inputemail)) {
            field_email.helperText = ""
        }

        if (inputpassword.isEmpty()){
            field_pass.helperText = "Required"
            regis_password.error = "Password cannot be empty"
        }

        if (inputpassword.isNotEmpty() && regis_password.text.toString().trim().length <= 5){
            field_pass.helperText = "Minimum 6 characters "
            regis_password.error = "Password must have 6 characters atleast"
        } else if ( regis_password.text.toString().trim().length > 5){
            field_pass.helperText = ""
        }

    }

    fun validateEmail(email: String): Boolean {
        val match = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return match.matcher(email).matches()
    }

    fun registerUser(full_name: String, email: String, password: String, phone_number: Int, address: String, city:String){
        val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

            viewModel.registerLiveData.observe(requireActivity()) {
                Log.d("abc", it)
                if (it == "201"){
                    toast = "Registration Succes"
                    Handler(Looper.getMainLooper()).postDelayed({
                        customToast.successToast(requireContext(), toast)
                        view?.findNavController()
                            ?.navigate(R.id.action_register_to_login)
                    },2000)


                } else if (it == "400"){
                    toast = "Email Already Exist"
                    Handler(Looper.getMainLooper()).postDelayed({
                        customToast.failureToast(requireContext(), toast)
                    },2000)

                } else if (it == "500"){
                    toast = "Internal Service Error"
                    Handler(Looper.getMainLooper()).postDelayed({
                        customToast.failureToast(requireContext(), toast)
                    },2000)

                } else {
                    toast = "No Internet Connection"
                    Handler(Looper.getMainLooper()).postDelayed({
                        customToast.failureToast(requireContext(), toast)
                    },2000)
                }
            }
        viewModel.registerUser(full_name, email, password, phone_number, address, city)
    }

}