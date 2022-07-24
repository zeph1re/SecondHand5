package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.adapter.HistoryAdapter
import and5.finalproject.secondhand5.viewmodel.HistoryViewModel
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
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
import kotlinx.android.synthetic.main.fragment_history.*


class History : Fragment() {

    private lateinit var historyAdapter : HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        loginViewModel.userToken(requireActivity()).observe(viewLifecycleOwner){ token->
            if (token!= ""){
                Handler(Looper.getMainLooper()).postDelayed({
                    initHistory()
                },500)
            }else{
                view.findNavController().navigate(R.id.action_history_to_userNotLogin)
            }
        }
    }

    private fun initHistory() {
        historyAdapter = HistoryAdapter {}

        val viewModelUser = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        val viewModelHistory = ViewModelProvider(requireActivity())[HistoryViewModel::class.java]

        viewModelUser.userToken(requireContext()).observe(viewLifecycleOwner) { token ->
            viewModelHistory.allHistory.observe(viewLifecycleOwner) {
                if (it != null) {
                    history_rv.layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                    history_rv.adapter = historyAdapter
                    historyAdapter.setHistoryList(it)
                    historyAdapter.notifyDataSetChanged()

                    if(historyAdapter.itemCount == 0){
                        notfound_history.visibility = View.VISIBLE
                    }else{
                        notfound_history.visibility = View.GONE
                    }

                }
            }
            viewModelHistory.getAllHistory(token)
        }


    }
}