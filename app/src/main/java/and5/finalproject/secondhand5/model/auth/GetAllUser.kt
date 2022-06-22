package and5.finalproject.secondhand5.model.auth


import com.google.gson.annotations.SerializedName

data class GetAllUser(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: Any,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: Long,
    @SerializedName("updatedAt")
    val updatedAt: String
)