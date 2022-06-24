package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Account : Fragment() {
    lateinit var userManager: UserManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireActivity())

        ubah_akun_card.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_account_to_profile)
        }

        setting_card.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_account_to_home)
        }

        logout_card.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_account_to_home)
            GlobalScope.launch {
                userManager.deleteDataUser()
            }

        }





    }
}