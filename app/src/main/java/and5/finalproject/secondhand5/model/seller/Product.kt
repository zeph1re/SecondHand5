package and5.finalproject.secondhand5.model.seller


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int
)