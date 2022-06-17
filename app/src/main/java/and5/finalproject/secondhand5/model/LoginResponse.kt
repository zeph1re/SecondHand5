package and5.finalproject.secondhand5.model

data class LoginResponse(
    val name : String,
    val password : String,
    val access_token : String,
    val message: String
)
