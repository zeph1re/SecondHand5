package and5.finalproject.secondhand5.model.seller


import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_name")
    val imageName: Any,
    @SerializedName("image_url")
    val imageUrl: Any,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)