package and5.finalproject.secondhand5.model.buyerproduct


import com.google.gson.annotations.SerializedName

data class UpdateBuyerOrderResponse(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_product")
    val imageProduct: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transcaction_date")
    val transcactionDate: Any,
    @SerializedName("updatedAt")
    val updatedAt: String
)