package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import and5.finalproject.secondhand5.view.adapter.NotificationAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.NotificationViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.notification_adapter.*

class Notification : Fragment() {

    lateinit var userManager: UserManager
    lateinit var getNotification: GetNotificationItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotification()
        readOrNot()
    }

    private fun initNotification() {
        userManager = UserManager(requireActivity())
        val notificationAdapter = NotificationAdapter()

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
            viewmodel.getNotification(it)
        }

    }

    private fun readOrNot() {
        if (getNotification.read) {
            read_or_not.visibility = View.GONE
        } else {
            read_or_not.visibility = View.VISIBLE
        }
    }


}