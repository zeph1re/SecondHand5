package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.auth.GetAllUser
import and5.finalproject.secondhand5.view.custom.CustomToast
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Login : Fragment() {

    lateinit var inputLoginEmail: String
    lateinit var inputLoginPassword: String
    lateinit var text: String
    private var customToast : CustomToast = CustomToast()
    lateinit var userManager : UserManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        userManager = UserManager(requireActivity())
        view.btn_back_login.setOnClickListener {
            activity?.onBackPressed()
        }

        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) {
            if (it != ""){
                    parent_login.visibility = View.GONE
                    view.findNavController().navigate(
                        R.id.action_login_to_account)
            }
        }


        view.btnlogin.setOnClickListener{
            inputLoginEmail = view.loginemail.text.toString()
            inputLoginPassword = view.loginpassword.text.toString()

            check()
            if (inputLoginEmail.isNotEmpty() && inputLoginPassword.isNotEmpty() ){

                view.loading.visibility = View.VISIBLE

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

        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            Log.d("abc", it)
            if (it == "201"){

                viewModel.userToken.observe(viewLifecycleOwner) { token ->
                    Log.d("userToken", token)
                    GlobalScope.launch {
                        userManager.saveDataUser(token)
                    }
                }

                text = "Login Success"
                Handler(Looper.getMainLooper()).postDelayed({
                    view?.loading?.visibility = View.GONE
                    customToast.successToast(requireContext(), text)
                    view?.findNavController()
                        ?.navigate(R.id.action_login_to_account)
                },2000)


            } else if (it == "401"){
                text = "Email or Password are Wrong"

                Handler(Looper.getMainLooper()).postDelayed({
                    view?.loading?.visibility = View.GONE
                    customToast.failureToast(requireContext(), text)
                },2000)

            } else if (it == "500"){
                text = "Internal Service Error"

                Handler(Looper.getMainLooper()).postDelayed({
                    view?.loading?.visibility = View.GONE
                    customToast.failureToast(requireContext(), text)
                },2000)

            } else {
                text = "No Internet Connection"

                Handler(Looper.getMainLooper()).postDelayed({
                    view?.loading?.visibility = View.GONE
                    customToast.failureToast(requireContext(), text)
                },2000)

            }
        }
        viewModel.loginUser(email, password)
    }



}