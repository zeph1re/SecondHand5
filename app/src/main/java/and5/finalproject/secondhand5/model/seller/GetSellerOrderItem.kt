package and5.finalproject.secondhand5.model.seller


import com.google.gson.annotations.SerializedName

data class GetSellerOrderItem(
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: User
)