package and5.finalproject.secondhand5.model.wishlist


import com.google.gson.annotations.SerializedName

data class DeleteWishlistResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)