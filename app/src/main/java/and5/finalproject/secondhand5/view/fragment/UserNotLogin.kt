package and5.finalproject.secondhand5.view.fragment

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_user_not_login.*


class UserNotLogin : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_not_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_userNotLogin_to_login)
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
                    viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) {
                        if (it == "" || it == null){
                            Navigation.findNavController(requireView()).navigate(R.id.action_userNotLogin_to_home)
                        }
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }





}