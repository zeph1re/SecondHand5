package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.auth.GetAllUser
import and5.finalproject.secondhand5.repository.UserRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor (private val userRepo : UserRepository):ViewModel(){

    var registerLiveData : SingeLiveEvent<String> = SingeLiveEvent ()
    var loginLiveData : SingeLiveEvent<String>  = SingeLiveEvent ()
    var getUserData : MutableLiveData<GetAllUser>  = MutableLiveData()

    fun registerLiveData(full_name: String, email : String, password: String, phone_number : Int, address: String, city:String){
        viewModelScope.launch {
            userRepo.regisUser( full_name, email, password, phone_number, address, city, registerLiveData )
        }

    }

    fun loginLiveData(email : String, password: String){
        viewModelScope.launch  {
            userRepo.loginUser(email, password, loginLiveData)
        }
    }

    fun getUserData(): MutableLiveData<GetAllUser>{
        return getUserData
    }


}