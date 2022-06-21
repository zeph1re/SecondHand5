package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.model.LoginResponse
import and5.finalproject.secondhand5.model.RegisterResponse
import and5.finalproject.secondhand5.network.ApiService
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.fragment_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

class UserRepository @Inject constructor(private val service: ApiService){

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

    fun loginUser(email : String, password: String, liveData: MutableLiveData<String>) {
        val apiClient : Call<LoginResponse> = service.loginUser(email, password)
        apiClient.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                //manggil data store
                Log.d("test", response.body()?.accessToken.toString())
                liveData.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                liveData.postValue(null)
            }
            }
        )
    }

}