package and5.finalproject.secondhand5.Room.Model

import and5.finalproject.secondhand5.model.buyerproduct.Category
import and5.finalproject.secondhand5.model.buyerproduct.User
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class GetProductHome (
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("Categories")
    val categories: List<Category>,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
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
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: @RawValue User,
    @SerializedName("user_id")
    val userId: Int
        )

