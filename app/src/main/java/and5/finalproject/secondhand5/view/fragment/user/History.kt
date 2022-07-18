package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.adapter.BannerAdapter
import and5.finalproject.secondhand5.view.adapter.HistoryAdapter
import and5.finalproject.secondhand5.viewmodel.HistoryViewModel
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*


class History : Fragment() {

    lateinit var historyAdapter : HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""||token==null){

                initHistory()
            }else{
                view.findNavController().navigate(R.id.action_notification_to_userNotLogin)
            }
        }
    }

    private fun initHistory() {
        historyAdapter = HistoryAdapter(){}

        val viewModelUser = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        val viewModelHistory = ViewModelProvider(requireActivity()).get(HistoryViewModel::class.java)

        viewModelUser.userToken(requireContext()).observe(viewLifecycleOwner) {
            viewModelHistory.allHistory.observe(viewLifecycleOwner) {
                if (it != null) {
                    history_rv.layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                    history_rv.adapter = historyAdapter
                    historyAdapter.setHistoryList(it)
                    historyAdapter.notifyDataSetChanged()
                }
            }
            viewModelHistory.getAllHistory(it)
        }


    }
}