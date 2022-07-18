package and5.finalproject.secondhand5.model.seller

import com.google.gson.annotations.SerializedName


data class UpdateProductBody(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String
)
