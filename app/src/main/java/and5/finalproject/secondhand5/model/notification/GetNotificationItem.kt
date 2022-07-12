package and5.finalproject.secondhand5.model.notification


import com.google.gson.annotations.SerializedName

data class GetNotificationItem(
    @SerializedName("base_price")
    val basePrice: String,
    @SerializedName("bid_price")
    val bidPrice: Any,
    @SerializedName("buyer_name")
    val buyerName: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("notification_type")
    val notificationType: String,
    @SerializedName("order_id")
    val orderId: Any,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("read")
    val read: Boolean,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("seller_name")
    val sellerName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: Any,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: User
)