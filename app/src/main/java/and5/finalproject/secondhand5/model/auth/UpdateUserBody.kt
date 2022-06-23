package and5.finalproject.secondhand5.model.auth

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class UpdateUserBody(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("image_url")
    val imageUrl: @RawValue Any? = null,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: Long,
)
