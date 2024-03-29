@file:Suppress("CascadeIf", "CascadeIf", "CascadeIf", "CascadeIf", "CascadeIf", "CascadeIf",
    "CascadeIf", "CascadeIf", "CascadeIf", "CascadeIf", "CascadeIf", "CascadeIf"
)

package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.connectivity.CheckConnectivity
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.custom_layout_change_password.view.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Account : Fragment() {
    lateinit var userManager: UserManager
    private var connectivity: CheckConnectivity = CheckConnectivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userManager = UserManager(requireActivity())
        return inflater.inflate(R.layout.fragment_account, container, false)
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ubah_akun_card.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_account_to_profile)
        }

        setting_card.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_account_to_settings)
        }

        logout_card.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_account_to_home)
            GlobalScope.launch {
                userManager.deleteDataUser()
            }
        }
        if (connectivity.isOnline(requireContext())) {
            val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
            loginViewModel .userToken(requireActivity()).observe(viewLifecycleOwner) {
                getUserData(it)
            }
        }


        //button click to show dialog
        ubah_password_card.setOnClickListener {
            //Inflate dialog with custom view
            val mDialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.custom_layout_change_password, null, false)
            //
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()


            mDialogView.confirm_button.setOnClickListener {
                //dialog dismiss
                mAlertDialog.dismiss()
                val currentPass = mDialogView.current_password.text.toString()
                val newPass = mDialogView.new_password.text.toString()
                val confirmPass = mDialogView.confirm_password.text.toString()
                val viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
                val viewmodel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
                viewmodel.userToken(requireActivity()).observe(viewLifecycleOwner) { token ->
                    viewModel.responseCodeUpdatePassword.observe(viewLifecycleOwner) {
                        Log.d("tes response ", it.toString())
                        if (it == "200") {
                            Toast.makeText(requireContext(), "Berhasil mengubah password", Toast.LENGTH_SHORT).show()
                        }  else if (it == "400") {
                            Toast.makeText(requireContext(), "\t\n" +
                                    "Wrong Password", Toast.LENGTH_SHORT).show()
                        } else if (it == "500") {
                            Toast.makeText(requireContext(), "\t\n" +
                                    "\t\n" +
                                    "Internal Service Error", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "\t\n" +
                                    "No Internet Connection", Toast.LENGTH_SHORT).show()
                        }
                    }
                    viewModel.updatePasswordData(token, currentPass, newPass, confirmPass)
                }

            }

            mDialogView.cancel_button.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }



}
    private fun getUserData(token:String){
        val viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        viewModel.getUserData(token)
        viewModel.getUserData.observe(viewLifecycleOwner) {
            if (it.imageUrl != null){
                Glide.with(requireActivity()).load( it.imageUrl).into(view!!.acc_image)
            }else{
                acc_image.setImageResource(R.drawable.pp)
            }



        }
    }
}