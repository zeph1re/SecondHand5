package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.model.history.GetHistoryItem
import and5.finalproject.secondhand5.network.ApiService
import javax.inject.Inject

class HistoryRepository @Inject constructor( private val service : ApiService){

    suspend fun getAllHistory (token : String) : List<GetHistoryItem>{
        return service.getAllHistory(token)
    }

    suspend fun getDetailHistory (token : String, id :Int) : GetHistoryItem {
        return service.getDetailHistory(token,id)

    }

}