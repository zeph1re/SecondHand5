package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.repository.UserRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor (val userRepo : UserRepository):ViewModel(){


}