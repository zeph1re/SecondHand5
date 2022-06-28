package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import and5.finalproject.secondhand5.network.ApiService
import javax.inject.Inject

class NotificationRepository @Inject constructor( val service : ApiService) {

    suspend fun getNotification(token : String): List<GetNotificationItem> {
        return service.getNotification(token)
    }

}