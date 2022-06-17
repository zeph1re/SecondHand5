package and5.finalproject.secondhand5.model

data class RegisterResponse(
    val id: Int,
    val full_name: String,
    val email: String,
    val password : String,
    val phone_number : String,
    val address : String,
    val image_url : String,
    val createAt : String,
    val updatedAt : String,
    val message: String
)
