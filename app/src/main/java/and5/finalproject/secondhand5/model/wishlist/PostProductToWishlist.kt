package and5.finalproject.secondhand5.model.wishlist


import com.google.gson.annotations.SerializedName

data class PostProductToWishlist(
    @SerializedName("name")
    val name: String,
    @SerializedName("Product")
    val product: Product
)