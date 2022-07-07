package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.auth.*
import and5.finalproject.secondhand5.network.ApiService
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: ApiService){

    private lateinit var userManager: UserManager

    fun regisUser(full_name: String, email : String, password: String, phone_number : Int, address: String, city:String, liveData: MutableLiveData<String>) {
        val apiClient: Call<RegisterResponse> = service.registerUser(full_name, email, password, phone_number, address, city)

        apiClient.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                liveData.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                liveData.postValue(null)
            }
            }
        )
    }

    fun loginUser(email : String, password: String, liveToken: MutableLiveData<String>,  liveCode: MutableLiveData<String>) {
        val apiClient : Call<LoginResponse> = service.loginUser(email, password)
        apiClient.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                liveToken.postValue(response.body()?.accessToken.toString())
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                liveCode.postValue(null)
            }
            }
        )
    }

    suspend fun getUserToken(token:String): GetAllUser{
        return service.getUserItem(token)
    }

    suspend fun updateUser(token:String, user : UpdateUserBody): GetAllUser{
        return service.updateUser(token, user)
    }

    suspend fun changePasswordUser(token: String, current: String, new: String, confirm: String): UpdatePasswordBody {
        return service.updatePassword(token,current,new, confirm)
    }

}