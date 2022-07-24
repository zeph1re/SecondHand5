package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.connectivity.CheckConnectivity
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.adapter.NotificationAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.NotificationViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_notification.*

class Notification : Fragment() {

    lateinit var userManager: UserManager
    private lateinit var notificationAdapter : NotificationAdapter
    private lateinit var read : String

    private var connectivity: CheckConnectivity = CheckConnectivity()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        read = "true"
        val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""||token==null){
                if (connectivity.isOnline(requireContext())) {
                    initNotification()
                }
            }else{
                view.findNavController().navigate(R.id.action_notification_to_userNotLogin)
            }
        }


//        Handler(Looper.getMainLooper()).postDelayed({
////            readOrNot()
//        },1000)

    }

    private fun initNotification() {
        userManager = UserManager(requireActivity())
        notificationAdapter = NotificationAdapter {
            if (read == "true"){
                    readStatus(it.id)
                read = "false"
            }

            Handler(Looper.getMainLooper()).postDelayed({
            view?.findNavController()?.navigate(R.id.notification)
            },300)
        }

        val viewmodel = ViewModelProvider(requireActivity())[NotificationViewModel::class.java]
        val viewmodelUser = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewmodelUser.userToken(requireActivity()).observe(viewLifecycleOwner) { it ->
            viewmodel.notificationLiveData.observe(viewLifecycleOwner) { it ->
                if (it != null) {
                    val sorted = it.sortedByDescending { it.createdAt }
                    rv_notification.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false  )
                    rv_notification.adapter = notificationAdapter

                    notificationAdapter.setNotificationList(sorted)

                    val recyclerViewState = rv_notification.layoutManager?.onSaveInstanceState()
                    notificationAdapter.notifyDataSetChanged()
                    rv_notification.layoutManager?.onRestoreInstanceState(recyclerViewState)

                    if(notificationAdapter.itemCount == 0){
                        notfound_notification.visibility = View.VISIBLE
                    }else{
                        notfound_notification.visibility = View.GONE
                    }


                }
            }
            viewmodel.getNotification(it, "")
        }

    }


    private fun readStatus(idNotif : Int){

        val viewModelNotification = ViewModelProvider(requireActivity())[NotificationViewModel::class.java]

        val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->

            if(token != ""){
                viewModelNotification.patchNotification(token, idNotif)
            }

        }

    }

}