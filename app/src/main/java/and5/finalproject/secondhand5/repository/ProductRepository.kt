package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.auth.LoginResponse
import and5.finalproject.secondhand5.model.buyerproduct.AddBuyerOrderResponse
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.seller.*
import and5.finalproject.secondhand5.network.ApiService
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi : ApiService) {

    lateinit var userManager: UserManager

    suspend fun getAllProduct(): List<GetProductItem>{
        return productApi.getAllProduct()
    }

    suspend fun getDetailProduct(id:Int): GetProductItem {
        return productApi.getDetailProduct(id)
    }

    suspend fun getSellerProduct(token:String) : List<GetSellerProductItem> {
        return productApi.getSellerProduct(token)
    }

    suspend fun addBuyerOrder(
        access_token : String,
        id: Int,
        bid_price: Int,
        liveCode: MutableLiveData<String>
    ) {
        val apiClient :Call<AddBuyerOrderResponse> = productApi.postBuyerOrder(access_token,id,bid_price)
        apiClient.enqueue(object : Callback<AddBuyerOrderResponse> {
            override fun onResponse(
                call: Call<AddBuyerOrderResponse>,
                response: Response<AddBuyerOrderResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<AddBuyerOrderResponse>, t: Throwable) {
                liveCode.postValue(null)
            }
        })


    }
    suspend fun getAllCategory(): List<Category>{
        return productApi.getAllCategory()
    }
    suspend fun getSellerCategory(): List<GetSellerCategoryItem> {
        return productApi.getSellerCategory()
    }

    fun addProduct(token:String, name : RequestBody, desc: RequestBody, price: RequestBody, category: RequestBody, location : RequestBody, image : MultipartBody.Part){

        val apiClient : Call<PostResponse> = productApi.postProduct(token, name, desc, price, category, location, image)
        apiClient.enqueue(object : Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {

            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }
        })

}
}