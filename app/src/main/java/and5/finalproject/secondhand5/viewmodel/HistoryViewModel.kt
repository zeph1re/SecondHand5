package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.history.GetHistoryItem
import and5.finalproject.secondhand5.repository.HistoryRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repo : HistoryRepository) : ViewModel(){

    // Get All User Order History
    private var historyLiveData = MutableLiveData<List<GetHistoryItem>>()
    val allHistory : LiveData<List<GetHistoryItem>> = historyLiveData

    fun getAllHistory (token : String) {
        viewModelScope.launch {
            val dataHistory = repo.getAllHistory(token)
            historyLiveData.value = dataHistory
        }
    }

}