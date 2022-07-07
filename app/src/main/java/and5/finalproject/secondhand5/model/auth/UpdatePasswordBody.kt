package and5.finalproject.secondhand5.model.auth


import com.google.gson.annotations.SerializedName
import retrofit2.Call

data class UpdatePasswordBody(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)