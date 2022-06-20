package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.custom.CustomProgressDialog
import and5.finalproject.secondhand5.view.custom.CustomToast
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class Login : Fragment() {

    lateinit var inputLoginEmail: String
    lateinit var inputLoginPassword: String
    lateinit var text: String
    private var customToast : CustomToast = CustomToast()
    private var customDialog : CustomProgressDialog = CustomProgressDialog()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.btnlogin.setOnClickListener{
            inputLoginEmail = view.loginemail.text.toString()
            inputLoginPassword = view.loginpassword.text.toString()



            check()
            if (inputLoginEmail.isNotEmpty() && inputLoginPassword.isNotEmpty() ){
                customDialog.showProgressDialog(requireActivity())
                loginUser(inputLoginEmail, inputLoginPassword)
            }
        }

        view.daftar2.setOnClickListener{
            view.findNavController().navigate(R.id.action_login_to_register)
        }
        return view
    }

    fun check(){

        if (inputLoginEmail.isEmpty()){
            field_login_email.helperText = "Required"
            loginemail.error = "Email cannot be empty"
        } else{
            field_login_email.helperText = ""
        }

        if (inputLoginPassword.isEmpty()){
            field_login_password.helperText = "Required"
            loginpassword.error = "Password cannot be empty"
        } else {
            field_login_password.helperText = ""
        }

    }

    fun loginUser(email : String, password: String){
        val viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        viewModel.loginLiveData.observe(requireActivity()) {
            Log.d("abc", it)
            if (it == "201"){
                view?.findNavController()
                    ?.navigate(R.id.action_login_to_account)
                text = "Login Success"
                customDialog.dismissProgressDialog(requireActivity())
                Handler(Looper.getMainLooper()).postDelayed({
                    customToast.successToast(requireContext(), text)
                },2000)


            } else if (it == "401"){
                text = "Email or Password are Wrong"
                customDialog.dismissProgressDialog(requireActivity())
                Handler(Looper.getMainLooper()).postDelayed({
                    customToast.failureToast(requireContext(), text)
                },2000)

            } else if (it == "500"){
                text = "Internal Service Error"
                customDialog.dismissProgressDialog(requireActivity())
                Handler(Looper.getMainLooper()).postDelayed({
                    customToast.failureToast(requireContext(), text)
                },2000)

            } else {
                text = "No Internet Connection"
                customDialog.dismissProgressDialog(requireActivity())
                Handler(Looper.getMainLooper()).postDelayed({
                    customToast.failureToast(requireContext(), text)
                },2000)

            }
        }
        viewModel.loginLiveData(email, password)
    }

}