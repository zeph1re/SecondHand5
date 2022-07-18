package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.connectivity.CheckConnectivity
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.notification.GetNotificationItem
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
import kotlinx.android.synthetic.main.notification_adapter.*

class Notification : Fragment() {

    lateinit var userManager: UserManager
    lateinit var getNotification: GetNotificationItem
    lateinit var notificationAdapter : NotificationAdapter
    lateinit var Read : String

    var connectivity: CheckConnectivity = CheckConnectivity()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Read = "true"
        val loginViewModel =ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""||token==null){
                if (connectivity.isOnline(requireContext())) {
                    initNotification()
                }
            }else{
                view.findNavController().navigate(R.id.action_notification_to_userNotLogin)
            }
        }


        Handler(Looper.getMainLooper()).postDelayed({
//            readOrNot()
        },1000)

    }

    private fun initNotification() {
        userManager = UserManager(requireActivity())
        notificationAdapter = NotificationAdapter(){
            if (Read == "true"){
                    readStatus(it.id)
                Read = "false"
            }

            Handler(Looper.getMainLooper()).postDelayed({
            view?.findNavController()?.navigate(R.id.notification)
            },300)
        }

        val viewmodel = ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)
        val viewmodelUser = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewmodelUser.userToken(requireActivity()).observe(viewLifecycleOwner) {
            viewmodel.notificationLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    it.sortedBy { it.createdAt }
                    rv_notification.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false  )
                    rv_notification.adapter = notificationAdapter

                    notificationAdapter.setNotificationList(it)
                    notificationAdapter.notifyDataSetChanged()

                }
            }
            viewmodel.getNotification(it, "")
        }

    }

    private fun initBuyerNotification() {
        userManager = UserManager(requireActivity())
        notificationAdapter = NotificationAdapter(){
            readStatus(it.id)
        }

        val viewmodel = ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)
        val viewmodelUser = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewmodelUser.userToken(requireActivity()).observe(viewLifecycleOwner) {
            viewmodel.notificationLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    rv_notification.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false  )
                    rv_notification.adapter = notificationAdapter

                    notificationAdapter.setNotificationList(it)
                    notificationAdapter.notifyDataSetChanged()

                }
            }
            viewmodel.getNotification(it, "buyer")
        }

    }


    private fun initSellerNotification() {
        userManager = UserManager(requireActivity())
        notificationAdapter = NotificationAdapter(){
            readStatus(it.id)
            view?.findNavController()?.navigate(R.id.notification)

        }

        val viewmodel = ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)
        val viewmodelUser = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewmodelUser.userToken(requireActivity()).observe(viewLifecycleOwner) {
            viewmodel.notificationLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    rv_notification.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false  )
                    rv_notification.adapter = notificationAdapter

                    notificationAdapter.setNotificationList(it)
                    notificationAdapter.notifyDataSetChanged()

                }
            }
            viewmodel.getNotification(it, "seller")
        }

    }



    private fun readOrNot() {
        if (getNotification.read) {
            read_or_not.visibility = View.GONE
        } else {
            read_or_not.visibility = View.VISIBLE
        }
    }

    fun readStatus(idNotif : Int){

        val viewModelNotification = ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)

        val loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->

            if(token != ""){
                viewModelNotification.patchNotification(token, idNotif)
            }

        }

    }

}