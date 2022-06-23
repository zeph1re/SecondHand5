package and5.finalproject.secondhand5.model.buyerproduct


import com.google.gson.annotations.SerializedName

data class AddBuyerOrderResponse(
    @SerializedName("buyer_id")
    val buyerId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)