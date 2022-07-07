package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.auth.GetAllUser
import and5.finalproject.secondhand5.model.auth.UpdatePasswordBody
import and5.finalproject.secondhand5.model.auth.UpdateUserBody
import and5.finalproject.secondhand5.repository.UserRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor (private val userRepo : UserRepository):ViewModel(){

    var registerLiveData : SingeLiveEvent<String> = SingeLiveEvent ()
    var loginLiveData : SingeLiveEvent<String>  = SingeLiveEvent ()
    var getUserData : MutableLiveData<GetAllUser>  = MutableLiveData()
    var updateUserData : MutableLiveData<GetAllUser>  = MutableLiveData()
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

    fun updateUserData(token:String, user : UpdateUserBody){
        viewModelScope.launch  {
            val updateUser = userRepo.updateUser(token, user)
            updateUserData.value  = updateUser
        }
    }

    fun updatePasswordData(token: String, current: String, new:String, confirm : String){
        viewModelScope.launch {
            userRepo.changePasswordUser(token,current,new, confirm, responseCodeUpdatePassword )
        }
    }


}