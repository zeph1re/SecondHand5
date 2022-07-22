package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.auth.GetAllUser
import and5.finalproject.secondhand5.repository.UserRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor (private val userRepo : UserRepository):ViewModel(){

    var registerLiveData : SingeLiveEvent<String> = SingeLiveEvent ()
    var loginLiveData : SingeLiveEvent<String>  = SingeLiveEvent ()
    var getUserData : MutableLiveData<GetAllUser>  = MutableLiveData()
    var updateUserData :  SingeLiveEvent<String>  = SingeLiveEvent ()

    var responseCodeUpdatePassword : SingeLiveEvent<String> = SingeLiveEvent()
    var userToken : MutableLiveData<String> = MutableLiveData()

    fun registerUser(full_name: String, email : String, password: String, phone_number : Int, address: String, city:String){
        viewModelScope.launch {
            userRepo.regisUser( full_name, email, password, phone_number, address, city, registerLiveData )
        }

    }


    fun loginUser(email : String, password: String){
        viewModelScope.launch  {
            userRepo.loginUser(email, password, userToken, loginLiveData)
        }
    }

    fun getUserData(token:String){
        viewModelScope.launch  {
            val dataUser = userRepo.getUserToken(token)
            getUserData.value  =dataUser
        }
    }

    fun updateUserData(token:String, fullName: String, email: String, password: String, number: String, address: String, image: MultipartBody.Part, city: String ){
        viewModelScope.launch  {
            val partName = fullName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partEmail= email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partPassword= password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partNumber =
                number.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partAddress = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partCity = city.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            userRepo.updateUser(token, partName, partEmail, partPassword, partNumber, partAddress, image, partCity, updateUserData )
        }
    }

    fun updatePasswordData(token: String, current: String, new:String, confirm : String){
        viewModelScope.launch {
            userRepo.changePasswordUser(token,current,new, confirm, responseCodeUpdatePassword )
        }
    }


}