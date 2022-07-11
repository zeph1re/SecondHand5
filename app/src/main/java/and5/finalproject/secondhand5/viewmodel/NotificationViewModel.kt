package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import and5.finalproject.secondhand5.repository.NotificationRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private var notifRepo : NotificationRepository) : ViewModel(){

    var notificationLiveData = MutableLiveData<List<GetNotificationItem>>()

    var userToken : MutableLiveData<String> = MutableLiveData()

    var responseCodePatchNotification : SingeLiveEvent<String> = SingeLiveEvent ()


    fun getNotification(token: String){
        viewModelScope.launch{
            val dataNotification = notifRepo.getNotification(token)
            notificationLiveData.value = dataNotification
        }
    }

    fun patchNotification(access_token:String, id: Int){
        viewModelScope.launch {
            notifRepo.patchSellerNotification(access_token, id, responseCodePatchNotification)
        }
    }
}