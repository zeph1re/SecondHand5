package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.adapter.NotificationAdapter
import and5.finalproject.secondhand5.viewmodel.NotificationViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notification.*

class Notification : Fragment() {

    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userManager = UserManager(requireActivity())
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun initNotification() {
        val notificationAdapter = NotificationAdapter()

        val viewmodel = ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)
        userManager.userToken.asLiveData().observe(viewLifecycleOwner){
            viewmodel.notificationLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    rv_list_offered.layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                    rv_list_offered.adapter = notificationAdapter()

                    notificationAdapter.setProductList(it)
                    notificationAdapter.notifyDataSetChanged()

                }
            }
            viewmodel.getNotification(it)
        }


    }
}