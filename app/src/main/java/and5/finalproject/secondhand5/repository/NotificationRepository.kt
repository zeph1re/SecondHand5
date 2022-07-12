package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import and5.finalproject.secondhand5.model.notification.PatchNotificationResponse
import and5.finalproject.secondhand5.network.ApiService
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NotificationRepository @Inject constructor( val service : ApiService) {

    suspend fun getNotification(token : String, notification_type : String): List<GetNotificationItem> {
        return service.getNotification(token, notification_type)
    }

    fun patchSellerNotification(
        access_token : String,
        id: Int,
        liveCode: MutableLiveData<String>
    ) {
        val apiClient : Call<PatchNotificationResponse> = service.patchNotification(access_token,id)
        apiClient.enqueue(object : Callback<PatchNotificationResponse> {
            override fun onResponse(
                call: Call<PatchNotificationResponse>,
                response: Response<PatchNotificationResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<PatchNotificationResponse>, t: Throwable) {
                liveCode.postValue(null)
            }
        })
    }

}