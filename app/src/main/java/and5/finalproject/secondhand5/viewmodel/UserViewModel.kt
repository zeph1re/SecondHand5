package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class UserViewModel constructor (val userRepo : UserRepository){


}