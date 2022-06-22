package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.datastore.UserManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class LoginViewModel : ViewModel(){
    lateinit var userManager: UserManager




    fun userToken(context: Context): LiveData<String> {
        userManager = UserManager(context)
        return userManager.userToken.asLiveData()
    }
}