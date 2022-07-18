package and5.finalproject.secondhand5.model.wishlist


import com.google.gson.annotations.SerializedName

data class GetWishlistProductItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: ProductX,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("user_id")
    val userId: Int
)