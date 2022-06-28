package and5.finalproject.secondhand5.model.seller


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)