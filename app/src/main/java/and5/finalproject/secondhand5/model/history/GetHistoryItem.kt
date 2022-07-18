package and5.finalproject.secondhand5.model.history


import com.google.gson.annotations.SerializedName

data class GetHistoryItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("user_id")
    val userId: Int
)