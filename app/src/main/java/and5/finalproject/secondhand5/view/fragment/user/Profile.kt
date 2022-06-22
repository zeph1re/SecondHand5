package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Profile : Fragment() {
    lateinit var userManager: UserManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userManager = UserManager(requireActivity())
        val viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) {
            Log.d("aaaaatoken", it)
            getUserData(it)
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

        }

    }

}