package and5.finalproject.secondhand5.model.seller


import com.google.gson.annotations.SerializedName

data class GetSellerCategoryItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)